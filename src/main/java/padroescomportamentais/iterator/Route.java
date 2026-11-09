package padroescomportamentais.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Aggregate: holds every station of the line.
 * Iterating the route directly (for-each) gives the local service;
 * expressIterator() gives the express service over the same stations.
 */
public class Route implements Iterable<Station> {

    private final List<Station> stations = new ArrayList<>();

    public Route(Station... stations) {
        this.stations.addAll(List.of(stations));
    }

    /** Local service: stops at every station. */
    @Override
    public Iterator<Station> iterator() {
        return stations.iterator();
    }

    /** Express service: stops only at express stations. */
    public Iterator<Station> expressIterator() {
        return new ExpressIterator(stations);
    }
}
