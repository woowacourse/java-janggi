package position;

public enum Row {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE;

    public Row move(int amount) {
        if (!canMove(amount)) {
            throw new IllegalArgumentException("이동할 수 없는 열입니다.");
        }
        return values()[ordinal() + amount];
    }

    public boolean canMove(int amount) {
        if (ordinal() + amount >= values().length || ordinal() + amount < 0) {
            return false;
        }
        return true;
    }
}
