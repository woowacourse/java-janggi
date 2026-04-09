package domain.board.palace;

public enum PalaceArea {
    TOP,
    BOTTOM,
    NONE;

    public boolean isSameArea(PalaceArea other) {
        return this != NONE && this == other;
    }
}
