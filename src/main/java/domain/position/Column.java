package domain.position;

public class Column extends Axis<Column> {

    public static final int MAX_COLUMN = 8;
    public static final int MIN_COLUMN = 0;

    public Column(int value) {
        super(value);
    }

    @Override
    protected int getMin() {
        return MIN_COLUMN;
    }

    @Override
    protected int getMax() {
        return MAX_COLUMN;
    }

    @Override
    protected Column create(int value) {
        return new Column(value);
    }
}
