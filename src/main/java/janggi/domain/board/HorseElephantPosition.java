package janggi.domain.board;

public enum HorseElephantPosition {

    HEHE(2, 3, 7, 8),
    HEEH(2, 3, 8, 7),
    EHEH(3, 2, 8, 7),
    EHHE(3, 2, 7, 8);

    private final int leftHorseColumn;
    private final int leftElephantColumn;
    private final int rightHorseColumn;
    private final int rightElephantColumn;

    HorseElephantPosition(
            int leftHorseColumn, int leftElephantColumn,
            int rightHorseColumn, int rightElephantColumn
    ) {
        this.leftHorseColumn = leftHorseColumn;
        this.leftElephantColumn = leftElephantColumn;
        this.rightHorseColumn = rightHorseColumn;
        this.rightElephantColumn = rightElephantColumn;
    }

    public int leftHorseColumn() {
        return leftHorseColumn;
    }

    public int leftElephantColumn() {
        return leftElephantColumn;
    }

    public int rightHorseColumn() {
        return rightHorseColumn;
    }

    public int rightElephantColumn() {
        return rightElephantColumn;
    }

}
