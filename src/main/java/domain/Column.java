package domain;

public enum Column {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);

    private final int column;

    Column(int column) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }
}
