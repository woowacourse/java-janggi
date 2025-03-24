package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.PieceType;

public class Sa extends Piece {
    private static final int SA_REACHABLE_RADIUS = 2;

    public Sa(Country country) {
        super(country, PieceType.SA);
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateReachableCoordinate(from, to);
        validateTarget(board, to);
    }

    private void validateTarget(JanggiBoard board, JanggiCoordinate to) {
        if (board.isOccupied(to) && isSameCountry(board.findPieceByCoordinate(to))) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
    }

    private void validateReachableCoordinate(JanggiCoordinate from, JanggiCoordinate to) {


        if (from.distanceTo(to) > SA_REACHABLE_RADIUS) {
            throw new IllegalArgumentException("[ERROR] 기물이 해당 위치로 이동할 수 없습니다.");
        }
    }

}
