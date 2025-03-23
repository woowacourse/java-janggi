package team;

import java.util.List;

public enum Team {

    HAN("한", "\u001B[31m", List.of(8, 7, 3, 2), 1),
    CHO("초", "\u001B[34m", List.of(2, 3, 7, 8), 10),
    ;

    private static final String RESET = "\u001B[0m";

    private final String teamName;
    private final String colorCode;
    private final List<Integer> maSangXCoordinates;
    private final int maSangYCoordinate;

    Team(String teamName, String colorCode, List<Integer> maSangXCoordinates, int maSangYCoordinate) {
        this.teamName = teamName;
        this.colorCode = colorCode;
        this.maSangXCoordinates = maSangXCoordinates;
        this.maSangYCoordinate = maSangYCoordinate;
    }

    public boolean isSameTeam(Team team) {
        return this.equals(team);
    }

    public String applyColor(String text) {
        return this.colorCode + text + RESET;
    }

    public String applyColorTeamName() {
        return applyColor(this.teamName);
    }

    public List<Integer> getMaSangXCoordinates() {
        return maSangXCoordinates;
    }

    public int getMaSangYCoordinate() {
        return maSangYCoordinate;
    }
}
