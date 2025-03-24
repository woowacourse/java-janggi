package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.PieceType;

public class Sa extends Piece implements DistanceMove {
    private static final int SA_REACHABLE_DISTANCE = 2;

    public Sa(Country country) {
        super(country, PieceType.SA);
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateReachableCoordinate(from, to, SA_REACHABLE_DISTANCE);
        validateTarget(board, from, to);
    }
}
