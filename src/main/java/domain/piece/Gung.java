package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.PieceType;

public class Gung extends Piece implements DistanceMove {
    private static final int GUNG_REACHABLE_DISTANCE = 2;

    public Gung(Country country) {
        super(country, PieceType.GUNG);
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateReachableDistanceCoordinate(from, to, GUNG_REACHABLE_DISTANCE);
        validateTarget(board, from, to);
    }
}
