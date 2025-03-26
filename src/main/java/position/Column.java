package position;

public enum Column {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I;

    public Column move(int amount) {
        if (!canMove(amount)) {
            throw new IllegalArgumentException("이동할 수 없는 열입니다. ");
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
