package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.List;
import java.util.Map;

public class Score {
    private static final double ADDITIONAL_POINT_FOR_RED = 1.5;
    private static final Map<PieceType, Integer> scores = Map.of(
            PieceType.GENERAL, 0,
            PieceType.CANNON, 7,
            PieceType.CHARIOT, 13,
            PieceType.ELEPHANT, 3,
            PieceType.GUARD, 3,
            PieceType.HORSE, 5,
            PieceType.SOLDIER, 2
    );

    private final List<Piece> pieces;

    public Score(final List<Piece> pieces) {
        this.pieces = pieces;
    }

    public double calculateTeamScore(final Team team) {
        int teamPoint = pieces.stream()
                .filter(piece -> piece.isSameTeam(team))
                .map(piece -> scores.get(piece.getPieceType()))
                .mapToInt(i -> i)
                .sum();
        if (team == Team.RED) {
            return teamPoint + ADDITIONAL_POINT_FOR_RED;
        }
        return teamPoint;
    }
}
