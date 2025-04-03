package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.PieceType;
import domain.piece.move.CastlePieceMove;
import domain.piece.move.DistanceMove;

import java.util.function.Predicate;

public class Sa extends Piece implements DistanceMove, CastlePieceMove {
    private static final int SA_REACHABLE_DISTANCE = 2;
    private static final Predicate<Integer> isReachAble = (dist) -> dist <= SA_REACHABLE_DISTANCE;

    public Sa(Country country) {
        super(country, PieceType.SA);
    }

    @Override
    public void validatePieceMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateCastleCoordinate(board, to);
        validateReachableDistanceCoordinate(from, to, isReachAble);
        validateCastlePieceMove(board, from, to);
    }
}
