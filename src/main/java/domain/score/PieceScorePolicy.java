package domain.score;

import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.EnumMap;
import java.util.Map;

public class PieceScorePolicy {
    private static final Map<PieceType, Double> PIECE_SCORES = createPieceScores();

    public double scoreOf(Piece piece) {
        return scoreOf(piece.getPieceType());
    }

    public double scoreOf(PieceType pieceType) {
        return PIECE_SCORES.get(pieceType);
    }

    private static Map<PieceType, Double> createPieceScores() {
        final Map<PieceType, Double> pieceScores = new EnumMap<>(PieceType.class);
        pieceScores.put(PieceType.KING, 0.0);
        pieceScores.put(PieceType.GUARD, 3.0);
        pieceScores.put(PieceType.ELEPHANT, 3.0);
        pieceScores.put(PieceType.HORSE, 5.0);
        pieceScores.put(PieceType.ROOK, 13.0);
        pieceScores.put(PieceType.CANNON, 7.0);
        pieceScores.put(PieceType.PAWN, 2.0);
        return pieceScores;
    }
}
