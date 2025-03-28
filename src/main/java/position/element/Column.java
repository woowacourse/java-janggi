package position.element;

public enum Column {

    ONE(),
    TWO(),
    THREE(),
    FOUR(),
    FIVE(),
    SIX(),
    SEVEN(),
    EIGHT(),
    NINE(),
    ;

    public boolean isFarLeft() {
        return ordinal() == 0;
    }

    public boolean isFarRight() {
        return ordinal() + 1 == values().length;
    }

    public boolean canMoveLeft(final int step) {
        return ordinal() - step >= 0;
    }

    public Column moveLeft() {
        return moveLeft(1);
    }

    public Column moveLeft(final int step) {
        if (canMoveLeft(step)) {
            return values()[ordinal() - step];
        }

        throw new IllegalStateException("움직일 수 없는 위치입니다.");
    }

    public boolean canMoveRight(final int step) {
        return ordinal() + step < values().length;
    }

    public Column moveRight() {
        return moveRight(1);
    }

    public Column moveRight(final int step) {
        if (canMoveRight(step)) {
            return values()[ordinal() + step];
        }

        throw new IllegalStateException("움직일 수 없는 위치입니다.");
    }

    public int calculateDifference(Column compare) {
        return Math.abs(this.ordinal() - compare.ordinal());
    }

    public boolean isLessThan(Column compare) {
        return this.ordinal() >= compare.ordinal();
    }
}
