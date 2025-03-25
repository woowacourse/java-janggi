package janggi.position;

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

    public boolean canMove(int step) {
        final int movedIndex = ordinal() + step;
        return values().length > movedIndex && movedIndex >= 0;
    }

    public Column move(int step) {
        if (canMove(step)) {
            return values()[ordinal() + step];
        }

        throw new IllegalArgumentException("[ERROR] 장기판 내에서만 이동할 수 있습니다.");
    }

}
