package janggi.domain;

import janggi.domain.piece.PieceType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {

    private final List<Position> positions;

    private Route(final List<Position> positions) {
        this.positions = positions;
    }

    public static Route of(final Position departure, final Position destination) {
        List<Position> positions = new ArrayList<>();
        positions.add(departure);
        int moveCount = calculateMoveCount(departure, destination);
        for (int i = 0; i < moveCount; i++) {
            positions.add(decideNextPosition(positions.getLast(), destination));
        }
        return excludeDepartureAndDestination(positions);
    }

    private static int calculateMoveCount(final Position departure, final Position destination) {
        return Math.max(Math.abs(destination.subtractRow(departure)), Math.abs(destination.subtractColumn(departure)));
    }

    private static Position decideNextPosition(final Position current, final Position destination) {
        int differenceOfRow = destination.subtractRow(current);
        int differenceOfColumn = destination.subtractColumn(current);
        return decideDirection(current, differenceOfRow, differenceOfColumn);
    }

    private static Position decideDirection(final Position current, int differenceOfRow, int differenceOfColumn) {
        int rowDirection = calculateDirection(differenceOfRow);
        int columnDirection = calculateDirection(differenceOfColumn);
        if (Math.abs(differenceOfRow) > Math.abs(differenceOfColumn)) {
            return current.adjust(rowDirection, 0);
        }
        if (Math.abs(differenceOfRow) < Math.abs(differenceOfColumn)) {
            return current.adjust(0, columnDirection);
        }
        return current.adjust(rowDirection, columnDirection);
    }

    private static int calculateDirection(final int targetDirection) {
        return (int) Math.signum(targetDirection);
    }

    private static Route excludeDepartureAndDestination(List<Position> positions) {
        validatePositionSize(positions);
        positions.removeFirst();
        positions.removeLast();
        return new Route(positions);
    }

    private static void validatePositionSize(final List<Position> positions) {
        if (positions.size() < 2) {
            throw new IllegalStateException("유효하지 않은 루트입니다");
        }
    }

    public boolean isExistSameTypePiece(Board board, PieceType pieceType) {
        return positions.stream()
                .filter(board::exists)
                .anyMatch(position -> board.getPiece(position).isSameType(pieceType));
    }

    public int countPieceInRoute(final Board board) {
        return (int) positions.stream()
                .filter(board::exists)
                .count();
    }

    List<Position> getPositions() {
        return Collections.unmodifiableList(positions);
    }
}
