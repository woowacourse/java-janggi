package janggi.piece;

import janggi.board.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Cannon extends Piece {

    public Cannon(final Side side) {
        super(side);
    }

    @Override
    public boolean canMove(Position start, Position end, Map<Position, Piece> board) {
        List<Position> path = findPath(start, end);
        return isOnePieceOnPath(path, board)
                && notExistsCannonOnPath(path, board)
                && isNotCannonTargetPiece(board.get(end))
                && isValidMovingRule(start, end);
    }

    private boolean isOnePieceOnPath(final List<Position> path, final Map<Position, Piece> board) {
        long countPieceOnPath = path.stream()
                .filter(board::containsKey)
                .count();
        return countPieceOnPath == 1;
    }

    private boolean notExistsCannonOnPath(final List<Position> path, final Map<Position, Piece> board) {
        return path.stream()
                .noneMatch(position -> board.containsKey(position) && board.get(position) instanceof Cannon);
    }

    private boolean isNotCannonTargetPiece(final Piece targetPiece) {
        return !(targetPiece instanceof Cannon);
    }

    private boolean isValidMovingRule(final Position start, final Position end) {
        return start.isHorizontalMove(end) || start.isVerticalMove(end);
    }

    private List<Position> findPath(final Position start, final Position end) {
        if (start.isHorizontalMove(end)) {
            return findHorizontalPath(start, end);
        }
        return findVerticalPath(start, end);
    }

    private List<Position> findHorizontalPath(final Position start, final Position end) {
        int differenceX = end.x() - start.x();
        int movingCount = Math.abs(differenceX);
        if (differenceX > 0) {
            return IntStream.range(1, movingCount)
                    .mapToObj(start::right)
                    .toList();
        }
        return IntStream.range(1, movingCount)
                .mapToObj(start::left)
                .toList();
    }

    private List<Position> findVerticalPath(final Position start, final Position end) {
        int differenceY = end.y() - start.y();
        int differenceYAmount = Math.abs(differenceY);
        if (differenceY > 0) {
            return IntStream.range(1, differenceYAmount)
                    .mapToObj(start::up)
                    .toList();
        }
        return IntStream.range(1, differenceYAmount)
                .mapToObj(start::down)
                .toList();
    }
}
