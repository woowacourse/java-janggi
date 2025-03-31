package domain.player;

public enum Team {
    CHO(true, 0, "초나라"),
    HAN(false, 9, "한나라");

    private final boolean isFirst;
    private final int initialRow;
    private final String name;

    Team(final boolean isFirst, final int initialRow, final String name) {
        this.isFirst = isFirst;
        this.initialRow = initialRow;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int calculateRowForPiece(final int row) {
        if (this.equals(HAN)) {
            return getInitialRow() - row;
        }
        if (this.equals(CHO)) {
            return getInitialRow() + row;
        }
        throw new IllegalArgumentException("팀 정보가 없습니다.");
    }

    public boolean isFirst() {
        return isFirst;
    }

    public int getInitialRow() {
        return initialRow;
    }
}
