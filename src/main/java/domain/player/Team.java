package domain.player;

public enum Team {
    CHO(9, 8, 7, 6),
    HAN(0, 1, 2, 3);

    private final int normalPieceRow;
    private final int jangRow;
    private final int poRow;
    private final int jolRow;

    Team(int normalPieceRow, int jangRow, int poRow, int jolRow) {
        this.normalPieceRow = normalPieceRow;
        this.jangRow = jangRow;
        this.poRow = poRow;
        this.jolRow = jolRow;
    }

    public boolean isHan() {
        return this == HAN;
    }

    public boolean isCho() {
        return this == CHO;
    }

    public int getNormalPieceRow() {
        return normalPieceRow;
    }

    public int getJangRow() {
        return jangRow;
    }

    public int getPoRow() {
        return poRow;
    }

    public int getJolRow() {
        return jolRow;
    }
}
