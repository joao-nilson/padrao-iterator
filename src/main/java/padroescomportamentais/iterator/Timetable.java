package padroescomportamentais.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Client: works with any station iterator, local or express. */
public class Timetable {

    public static List<String> stopNames(Iterator<Station> service) {
        List<String> names = new ArrayList<>();
        while (service.hasNext()) {
            names.add(service.next().getName());
        }
        return names;
    }

    public static int countStops(Iterator<Station> service) {
        return stopNames(service).size();
    }
}
