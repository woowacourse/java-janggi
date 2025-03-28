package domain.board;

import domain.piece.Piece;
import domain.piece.PieceColor;

public class Score {

    private final Board board;

    public Score(Board board) {
        this.board = board;
    }

    public double calculatePieceScore(PieceColor pieceColor) {
        double pieceScore = board.getPieceByColor(pieceColor).stream()
                .mapToDouble(Piece::getPieceScore)
                .sum();
        if (pieceColor == PieceColor.RED) {
            pieceScore += 1.5;
        }
        return pieceScore;
    }
}
