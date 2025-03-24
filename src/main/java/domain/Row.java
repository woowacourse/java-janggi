package domain;

public enum Row {
    ZERO(0), NINE(9), EIGHT(8), SEVEN(7), SIX(6), FIVE(5), FOUR(4), THREE(3), TWO(2), ONE(1);

    final int row;

    Row(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }
}
