package domain.score;

import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ScoreCalculator {

    public Score calculateTotalScoreOfPieces(final List<Piece> pieces) {
        Map<PieceType, Integer> counts = initializeCounts();
        calculateCountByPiece(pieces, counts);

        Score totalScore = new Score(0);
        for (PieceType pieceType : PieceType.values()) {
            Score scoreByPieceType = new Score(counts.get(pieceType)).multiply(pieceType.score());
            totalScore = totalScore.plus(scoreByPieceType);
        }
        return totalScore;
    }

    private void calculateCountByPiece(List<Piece> pieces, Map<PieceType, Integer> counts) {
        for (Piece piece : pieces) {
            counts.compute(piece.type(), (k, v) -> v + 1);
        }
    }

    private Map<PieceType, Integer> initializeCounts() {
        Map<PieceType, Integer> counts = new EnumMap<>(PieceType.class);
        for (PieceType pieceType : PieceType.values()) {
            counts.put(pieceType, 0);
        }
        return counts;
    }
}
