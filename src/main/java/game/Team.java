package game;

import java.util.EnumSet;
import piece.Pieces;

public enum Team {
    RED("red", 73.5),
    GREEN("green", 72),
    NONE("none", 0);

    private final String expression;
    private final double initialScore;

    Team(String expression, double initialScore) {
        this.expression = expression;
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

    public static Team findByExpression(String expression) {
        return EnumSet.allOf(Team.class).stream()
                .filter(team -> team.getExpression().equals(expression))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 해당하는 팀이 없습니다."));
    }

    public double calculateFinalScore(Pieces catchPieces) {
        return initialScore - catchPieces.calculateTotalScore();
    }

    public String getExpression() {
        return expression;
    }

    public boolean isNotDecided() {
        return this == NONE;
    }
}
