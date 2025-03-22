package domain.piece;

import domain.Country;
import domain.JanggiCoordinate;
import domain.PieceType;
import domain.board.JanggiBoard;

public class Sa extends Piece {
    private static final int SA_REACHABLE_RADIUS = 2;

    public Sa(Country country) {
        super(country, PieceType.SA);
    }

    @Override
    public void validateMove(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        validateMoveCoordinate(board, from, to);
        validateMaMoveStrategy(from, to);
        validateTarget(board, to);
    }

    private void validateMaMoveStrategy(JanggiCoordinate from, JanggiCoordinate to) {
        validateReachableCoordinate(from, to);
    }

    private void validateMoveCoordinate(JanggiBoard board, JanggiCoordinate from, JanggiCoordinate to) {
        if (board.isOutOfBoundary(from) || board.isOutOfBoundary(to)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 좌표위치 입니다.");
        }
    }

    private void validateTarget(JanggiBoard board, JanggiCoordinate to) {
        if (board.isOccupied(to) && isSameCountry(board.findPieceByCoordinate(to))) {
            throw new IllegalArgumentException("[ERROR] 나의 기물이 이미 해당 위치에 있습니다.");
        }
    }

    private void validateReachableCoordinate(JanggiCoordinate from, JanggiCoordinate to) {
        int rowDst = Math.abs(from.getRow() - to.getRow());
        int colDst = Math.abs(from.getCol() - to.getCol());

        if (square(rowDst) + square(colDst) > SA_REACHABLE_RADIUS) {
            throw new IllegalArgumentException("[ERROR] 기물이 해당 위치로 이동할 수 없습니다.");
        }
    }

    private int square(int n) {
        return (int) Math.pow(n, 2);
    }
}
