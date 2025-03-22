package domain.position;

public class Row extends Axis<Row> {

    public static final int MAX_ROW = 9;
    public static final int MIN_ROW = 0;

    public Row(int row) {
        super(row);
    }

    @Override
    protected int getMin() {
        return MIN_ROW;
    }

    @Override
    protected int getMax() {
        return MAX_ROW;
    }

    @Override
    protected Row create(int value) {
        return new Row(value);
    }
}
