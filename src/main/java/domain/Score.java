package domain;

import static java.util.Map.entry;

import domain.piece.PieceType;
import java.util.Arrays;
import java.util.Map;

public final class Score {

    private static final Map<PieceType, Integer> scores;
    private static final double RED_BENEFIT = 1.5;

    static {
        scores = Map.ofEntries(
                entry(PieceType.GENERAL, 0),
                entry(PieceType.SOLDIER, 2),
                entry(PieceType.GUARD, 3),
                entry(PieceType.ELEPHANT, 3),
                entry(PieceType.HORSE, 5),
                entry(PieceType.CANNON, 7),
                entry(PieceType.CHARIOT, 13)
        );
    }

    private Score() {
    }

    public static double adjustScore(final double score) {
        return score + RED_BENEFIT;
    }

    public static int calculate(final Map<PieceType, Integer> pieceCounts) {
        return Arrays.stream(PieceType.values())
                .mapToInt(pieceType -> calculatePieceScore(pieceCounts, pieceType))
                .sum();
    }

    private static int calculatePieceScore(final Map<PieceType, Integer> pieceCounts, final PieceType pieceType) {
        return pieceCounts.getOrDefault(pieceType, 0) * scores.get(pieceType);
    }
}
