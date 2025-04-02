package domain.score;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreCalculator {

    public static final Score HAN_BONUS_SCORE = new Score(1.5);

    public Map<Team, Score> calculateTotalScoreByTeam(final List<Piece> piecesOfCho,
                                                      final List<Piece> piecesOfHan) {
        Map<Team, Score> totalScoreByTeam = new HashMap<>();
        totalScoreByTeam.put(Team.CHO, calculateTotalScoreOfPieces(piecesOfCho));
        totalScoreByTeam.put(Team.HAN, calculateTotalScoreOfPieces(piecesOfHan).plus(HAN_BONUS_SCORE));
        return totalScoreByTeam;
    }

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

    private Map<PieceType, Integer> initializeCounts() {
        Map<PieceType, Integer> counts = new EnumMap<>(PieceType.class);
        for (PieceType pieceType : PieceType.values()) {
            counts.put(pieceType, 0);
        }
        return counts;
    }

    private void calculateCountByPiece(List<Piece> pieces, Map<PieceType, Integer> counts) {
        for (Piece piece : pieces) {
            counts.compute(piece.type(), (k, v) -> v + 1);
        }
    }
}
