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

    public Column move(Column column) {
        int step = column.ordinal();
        if(canMove(step)){
            return values()[ordinal() + step];
        }

        throw new IllegalStateException("[ERROR] 장기판 내에서만 이동할 수 있습니다.");
    }

    public Column move(int step) {
        if (canMove(step)) {
            return values()[ordinal() + step];
        }

        throw new IllegalStateException("[ERROR] 장기판 내에서만 이동할 수 있습니다.");
    }

    private boolean canMove(int step) {
        final int movedIndex = ordinal() + step;
        return values().length > movedIndex && movedIndex >= 0;
    }
}
