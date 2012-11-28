package db;

public class Column {
    public static final String[] NAMES = new String[] { "ro", "en" };

    public static final int RO_INDEX = 0;
    public static final int EN_INDEX = 1;

    private int index;

    private String value;

    public Column(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public String getName() {
        return NAMES[index];
    }

    public String getValue() {
        return value;
    }
}
