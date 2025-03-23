package team;

public enum Team {

    HAN("한", "\u001B[31m"),
    CHO("초", "\u001B[34m"),
    ;

    private static final String RESET = "\u001B[0m";

    private final String teamName;
    private final String colorCode;

    Team(String teamName, String colorCode) {
        this.teamName = teamName;
        this.colorCode = colorCode;
    }

    public String applyColor(String text) {
        return this.colorCode + text + RESET;
    }

    public String applyColorTeamName() {
        return applyColor(this.teamName);
    }
}
