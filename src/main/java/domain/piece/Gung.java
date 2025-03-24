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

    private void validateTarget(JanggiBoard board, JanggiCoordinate to) {
        if (board.isOccupied(to) && isSameCountry(board.findPieceByCoordinate(to))) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateReachableCoordinate(from, to, GUNG_REACHABLE_DISTANCE);
        validateTarget(board, to);
    }
}
