package padroescomportamentais.iterator;

public class Station {

    private final String name;
    private final boolean expressStop;

    public Station(String name, boolean expressStop) {
        this.name = name;
        this.expressStop = expressStop;
    }

    public String getName() {
        return name;
    }

    public boolean isExpressStop() {
        return expressStop;
    }
}
