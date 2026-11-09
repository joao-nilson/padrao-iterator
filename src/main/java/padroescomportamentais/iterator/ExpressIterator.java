package padroescomportamentais.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ExpressIterator implements Iterator<Station> {

    private final List<Station> stations;
    private int position = 0;

    public ExpressIterator(List<Station> stations) {
        this.stations = stations;
    }

    @Override
    public boolean hasNext() {
        while (position < stations.size() && !stations.get(position).isExpressStop()) {
            position++;
        }
        return position < stations.size();
    }

    @Override
    public Station next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return stations.get(position++);
    }
}
