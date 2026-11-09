package padroescomportamentais.iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TimetableTest {

    private Route route;

    @BeforeEach
    void setUp() {
        route = new Route(
                new Station("Central", true),
                new Station("Park", false),
                new Station("Market", false),
                new Station("Airport", true),
                new Station("Harbor", false),
                new Station("Terminal", true)
        );
    }

    @Test
    void localServiceShouldStopAtEveryStation() {
        List<String> stops = Timetable.stopNames(route.iterator());
        assertEquals(List.of("Central", "Park", "Market", "Airport", "Harbor", "Terminal"), stops);
    }

    @Test
    void expressServiceShouldStopOnlyAtExpressStations() {
        List<String> stops = Timetable.stopNames(route.expressIterator());
        assertEquals(List.of("Central", "Airport", "Terminal"), stops);
    }

    @Test
    void expressStopsShouldBeASubsetOfLocalStopsInSameOrder() {
        List<String> local = Timetable.stopNames(route.iterator());
        List<String> express = Timetable.stopNames(route.expressIterator());
        assertTrue(local.containsAll(express));
        assertTrue(express.size() < local.size());
        assertEquals(express, local.stream().filter(express::contains).collect(java.util.stream.Collectors.toList()));
    }

    @Test
    void shouldCountStopsPerService() {
        assertEquals(6, Timetable.countStops(route.iterator()));
        assertEquals(3, Timetable.countStops(route.expressIterator()));
    }

    @Test
    void forEachShouldUseLocalService() {
        int count = 0;
        for (Station station : route) {
            assertNotNull(station.getName());
            count++;
        }
        assertEquals(6, count);
    }

    @Test
    void iteratorsShouldBeIndependent() {
        Iterator<Station> first = route.expressIterator();
        Iterator<Station> second = route.expressIterator();
        assertEquals("Central", first.next().getName());
        assertEquals("Airport", first.next().getName());
        assertEquals("Central", second.next().getName());
    }

    @Test
    void hasNextShouldBeIdempotent() {
        Iterator<Station> express = route.expressIterator();
        express.next(); // Central
        assertTrue(express.hasNext());
        assertTrue(express.hasNext());
        assertEquals("Airport", express.next().getName());
    }

    @Test
    void shouldThrowWhenServiceIsFinished() {
        Iterator<Station> express = route.expressIterator();
        Timetable.stopNames(express);
        assertFalse(express.hasNext());
        assertThrows(NoSuchElementException.class, express::next);
    }

    @Test
    void expressShouldBeEmptyWhenNoExpressStations() {
        Route localOnly = new Route(new Station("A", false), new Station("B", false));
        assertEquals(0, Timetable.countStops(localOnly.expressIterator()));
        assertEquals(2, Timetable.countStops(localOnly.iterator()));
    }

    @Test
    void emptyRouteShouldHaveNoStops() {
        Route empty = new Route();
        assertEquals(0, Timetable.countStops(empty.iterator()));
        assertEquals(0, Timetable.countStops(empty.expressIterator()));
    }
}
