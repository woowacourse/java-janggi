package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.PieceType;

import java.util.function.Predicate;

public class Sa extends Piece implements DistanceMove {
    private static final int SA_REACHABLE_DISTANCE = 2;
    private static final Predicate<Integer> isReachAble = (dist) -> dist <= SA_REACHABLE_DISTANCE;

    public Sa(Country country) {
        super(country, PieceType.SA);
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateCoordinate(to);
        validateReachableDistanceCoordinate(from, to, isReachAble);
        validateTarget(board, from, to);
    }
}
