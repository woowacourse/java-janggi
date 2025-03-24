package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.PieceType;

import java.util.function.Predicate;

public class Gung extends Piece implements DistanceMove {
    private static final int GUNG_REACHABLE_DISTANCE = 2;
    private static final Predicate<Integer> isReachAble = (dist) -> dist <= GUNG_REACHABLE_DISTANCE;

    public Gung(Country country) {
        super(country, PieceType.GUNG);
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateCoordinate(to);
        validateReachableDistanceCoordinate(from, to, isReachAble);
        validateTarget(board, from, to);
    }
}
