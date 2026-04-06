package janggi.domain.score;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.team.Team;

import java.util.Map;

public class Score {
    public static final double SECOND_TURN_BONUS = 1.5;

    private final double hanScore;
    private final double choScore;

    public Score(Map<Position, Piece> board) {
        hanScore = calculateScore(board, SECOND_TURN_BONUS, Team.HAN);
        choScore = calculateScore(board, 0.0, Team.CHO);
    }

    public double getHanScore() {
        return hanScore;
    }

    public double getChoScore() {
        return choScore;
    }

    private double calculateScore(Map<Position, Piece> board, double bonus, Team team) {
        return bonus + board.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToDouble(piece -> piece.getType().getScore())
                .sum();
    }
}
