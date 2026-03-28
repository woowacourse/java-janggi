package domain.piece;

public enum Team {
    CHO("초", "\u001B[34m", 1, 2, 3, 4),
    HAN("한", "\u001B[31m", 10, 9, 8, 7);

    private static final String RESET = "\u001B[0m";

    private final String teamName;
    private final String colorCode;
    private final int backRow;
    private final int generalRow;
    private final int cannonRow;
    private final int soldierRow;

    Team(String teamName, String colorCode, int backRow, int generalRow, int cannonRow, int soldierRow) {
        this.teamName = teamName;
        this.colorCode = colorCode;
        this.backRow = backRow;
        this.generalRow = generalRow;
        this.cannonRow = cannonRow;
        this.soldierRow = soldierRow;
    }

    public String colorize(String text) {
        return colorCode + text + RESET;
    }

    public int getBackRow() {
        return backRow;
    }

    public int getGeneralRow() {
        return generalRow;
    }

    public int getCannonRow() {
        return cannonRow;
    }

    public int getSoldierRow() {
        return soldierRow;
    }

    public int forwardRowDirection() {
        if (this == CHO) {
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return teamName;
    }
}
