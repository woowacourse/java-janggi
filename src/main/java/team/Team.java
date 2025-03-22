package team;

public enum Team {

    HAN(1, "한", "\u001B[31m"),
    CHO(-1, "초", "\u001B[34m"),
    ;

    private static final String RESET = "\u001B[0m";

    private final int direction;
    private final String teamName;
    private final String colorCode;

    Team(int direction, String teamName, String colorCode) {
        this.direction = direction;
        this.teamName = teamName;
        this.colorCode = colorCode;
    }

    public int getDirection() {
        return direction;
    }

    public String applyColor(String text) {
        return this.colorCode + text + RESET;
    }

    public String applyColorTeamName() {
        return applyColor(this.teamName);
    }
}
