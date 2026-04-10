package domain.score;

import domain.Side;
import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;

public class ScorePolicy {
    private static final double HAN_KOMI = 1.5;
    private static final Map<PieceType, Integer> PIECE_SCORES = Map.of(
            PieceType.GENERAL, 0,
            PieceType.CHARIOT, 13,
            PieceType.CANNON, 7,
            PieceType.HORSE, 5,
            PieceType.ELEPHANT, 3,
            PieceType.GUARD, 3,
            PieceType.SOLDIER, 2
    );

    public double calculate(Board board, Side side) {
        int pieceScore = board.getBoard().values().stream()
                .filter(piece -> piece.isAlly(side))
                .mapToInt(this::calculatePieceScore)
                .sum();

        if (side == Side.HAN) {
            return pieceScore + HAN_KOMI;
        }
        return pieceScore;
    }

    private int calculatePieceScore(Piece piece) {
        PieceType pieceType = piece.getPieceType();
        if (!PIECE_SCORES.containsKey(pieceType)) {
            throw new IllegalArgumentException("지원하지 않는 기물 점수입니다.");
        }
        return PIECE_SCORES.get(pieceType);
    }
}
