package janggi.domain.score;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.team.Team;

import java.util.Map;

public class Score {
    private static final double SECOND_TURN_BONUS = 1.5;

    private final double hanScore;
    private final double choScore;

    public Score(double hanScore, double choScore) {
        this.hanScore = hanScore;
        this.choScore = choScore;
    }

    public static Score from(Map<Position, Piece> board) {
        double hanScore = calculateScore(board, SECOND_TURN_BONUS, Team.HAN);
        double choScore = calculateScore(board, 0.0, Team.CHO);
        return new Score(hanScore, choScore);
    }

    public double getHanScore() {
        return hanScore;
    }

    public double getChoScore() {
        return choScore;
    }

    private static double calculateScore(Map<Position, Piece> board, double bonus, Team team) {
        return bonus + board.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToDouble(Piece::getScore)
                .sum();
    }
}
