package domain;

import domain.piece.Piece;
import domain.piece.TeamType;
import java.util.List;

public class ScoreCalculator {
    private static final double HAN_BONUS_SCORE = 1.5;

    private final List<Piece> alivePieces;

    public ScoreCalculator(List<Piece> alivePieces) {
        this.alivePieces = alivePieces;
    }

    public double calculateChoScore() {
        return calculateScore(TeamType.CHO);
    }

    public double calculateHanScore() {
        double score = calculateScore(TeamType.HAN);
        return score + HAN_BONUS_SCORE;
    }

    private double calculateScore(TeamType teamType) {
        return alivePieces.stream()
                .filter(piece -> piece.getTeamType().equals(teamType))
                .mapToDouble(piece -> piece.getType().getScore())
                .sum();
    }
}
