package domain.score;

import domain.board.Board;
import domain.board.PiecePosition;
import domain.piece.TeamColor;

public class PieceScoreCalculator {
    private static final double HAN_BONUS_SCORE = 1.5;

    private final PieceScorePolicy pieceScorePolicy;

    public PieceScoreCalculator() {
        this(new PieceScorePolicy());
    }

    public PieceScoreCalculator(PieceScorePolicy pieceScorePolicy) {
        this.pieceScorePolicy = pieceScorePolicy;
    }

    public PieceScore calculate(Board board) {
        final double choScore = calculateTeamScore(board, TeamColor.CHO);
        final double hanScore = calculateTeamScore(board, TeamColor.HAN) + HAN_BONUS_SCORE;
        return new PieceScore(choScore, hanScore);
    }

    private double calculateTeamScore(Board board, TeamColor teamColor) {
        return board.findPiecesByTeam(teamColor).stream()
                .map(PiecePosition::piece)
                .mapToDouble(pieceScorePolicy::scoreOf)
                .sum();
    }
}
