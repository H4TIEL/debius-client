package ml.debius.systems.instance;

public class DataHolder {

    private Data data;

    private final static DataHolder INSTANCE = new DataHolder();

    private DataHolder() {}

    public static DataHolder getInstance() {
        return INSTANCE;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }
}
