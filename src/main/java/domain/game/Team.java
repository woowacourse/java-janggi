package domain.game;

public enum Team {
    CHO("초", 1, 2, 3, 4),
    HAN("한", 10, 9, 8, 7);

    private final String teamName;
    private final int backRow;
    private final int generalRow;
    private final int cannonRow;
    private final int soldierRow;

    Team(String teamName, int backRow, int generalRow, int cannonRow, int soldierRow) {
        this.teamName = teamName;
        this.backRow = backRow;
        this.generalRow = generalRow;
        this.cannonRow = cannonRow;
        this.soldierRow = soldierRow;
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
