package piece;

import java.util.EnumMap;
import java.util.Map;

public enum Team {
    RED(1, 1.5),
    BLUE(10, 0.0);

    private final int initRow;
    private final double bonusScore;

    Team(final int initRow, final double bonusScore) {
        this.initRow = initRow;
        this.bonusScore = bonusScore;
    }

    public static Map<Team, Double> initializeScoreBoard() {
        Map<Team, Double> scoreBoard = new EnumMap<>(Team.class);
        for (Team team : values()) {
            scoreBoard.put(team, team.bonusScore);
        }
        return scoreBoard;
    }

    public int convertRowOffsetByTeam(int offset) {
        if (this == Team.BLUE) {
            return -offset;
        }
        return offset;
    }

    public int getInitRow() {
        return initRow;
    }

}
