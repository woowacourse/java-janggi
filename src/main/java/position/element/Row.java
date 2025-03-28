package position.element;

public enum Row {

    ONE(),
    TWO(),
    THREE(),
    FOUR(),
    FIVE(),
    SIX(),
    SEVEN(),
    EIGHT(),
    NINE(),
    TEN(),
    ;

    public boolean isTop() {
        return ordinal() == 0;
    }

    public boolean isBottom() {
        return ordinal() + 1 == values().length;
    }

    public boolean canMoveUp(final int step) {
        return ordinal() - step >= 0;
    }

    public Row moveUp() {
        return moveUp(1);
    }

    public Row moveUp(final int step) {
        if (canMoveUp(step)) {
            return values()[ordinal() - step];
        }

        throw new IllegalStateException("움직일 수 없는 위치입니다.");
    }

    public boolean canMoveDown(final int step) {
        return ordinal() + step < values().length;
    }

    public Row moveDown() {
        return moveDown(1);
    }

    public Row moveDown(final int step) {
        if (canMoveDown(step)) {
            return values()[ordinal() + step];
        }

        throw new IllegalStateException("움직일 수 없는 위치입니다.");
    }

    public int calculateDifference(Row compare) {
        return Math.abs(this.ordinal() - compare.ordinal());
    }

    public boolean isGreaterThan(Row compare) {
        return this.ordinal() <= compare.ordinal();
    }

    public boolean isLessThan(Row compare) {
        return this.ordinal() >= compare.ordinal();
    }
}
