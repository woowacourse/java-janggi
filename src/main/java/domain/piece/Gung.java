package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.PieceType;

public class Gung extends Piece {
    private static final int GUNG_REACHABLE_RADIUS = 2;

    public Gung(Country country) {
        super(country, PieceType.GUNG);
    }

    private void validateTarget(JanggiBoard board, JanggiCoordinate to) {
        if (board.isOccupied(to) && isSameCountry(board.findPieceByCoordinate(to))) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
    }

    private void validateReachableCoordinate(JanggiCoordinate from, JanggiCoordinate to) {
        int rowDst = Math.abs(from.row() - to.row());
        int colDst = Math.abs(from.col() - to.col());

        if (square(rowDst) + square(colDst) > GUNG_REACHABLE_RADIUS) {
            throw new IllegalArgumentException("[ERROR] 궁이 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateReachableCoordinate(from, to);
        validateTarget(board, to);
    }

    private int square(int n) {
        return (int) Math.pow(n, 2);
    }
}
