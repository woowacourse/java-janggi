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
        validateTarget(board, to);
    }

    private void validateTarget(JanggiBoard board, JanggiCoordinate to) {
        if (board.isOccupied(to) && isSameCountry(board.findPieceByCoordinate(to))) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
    }
}
