package game;

import java.util.Arrays;
import piece.Pieces;

public enum Team {
    RED(1, 73.5),
    GREEN(2, 72),
    NONE(3, 0);

    private final int id;
    private final double initialScore;

    Team(int id, double initialScore) {
        this.id = id;
        this.initialScore = initialScore;
    }

    public static Team findOpponentBy(Team team) {
        if (team == RED) {
            return GREEN;
        }
        if (team == GREEN) {
            return RED;
        }
        throw new IllegalStateException("[ERROR] 유효하지 않은 팀입니다.");
    }

    public static Team findById(int id) {
        return Arrays.stream(Team.values())
                .filter(team -> team.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] id에 해당하는 팀이 없습니다."));
    }

    public static double calculateFinalScore(Team team, Pieces catchPieces) {
        return team.initialScore - catchPieces.calculateTotalScore();
    }

    public static Team findLatter() {
        return Team.RED;
    }

    public int getId() {
        return id;
    }

    public boolean isNotDecided() {
        return this == NONE;
    }
}
