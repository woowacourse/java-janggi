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
    public boolean canMove(final Position start, final Position end, final Map<Position, Piece> board) {
        List<Position> path = findPath(start, end);
        return isOnePieceOnPath(board, path)
                && notExistsCannonOnPath(board, path)
                && isNotCannonTargetPiece(board.get(end))
                && isValidMovingRule(start, end);
    }

    private boolean isOnePieceOnPath(final Map<Position, Piece> board, final List<Position> path) {
        long countPieceOnPath = path.stream()
                .filter(board::containsKey)
                .count();
        return countPieceOnPath == 1;
    }

    private boolean notExistsCannonOnPath(final Map<Position, Piece> board, final List<Position> path) {
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
        if (start.isRight(end)) {
            return IntStream.range(1, start.absDeltaX(end))
                    .mapToObj(start::right)
                    .toList();
        }
        return IntStream.range(1, start.absDeltaX(end))
                .mapToObj(start::left)
                .toList();
    }

    private List<Position> findVerticalPath(final Position start, final Position end) {
        if (start.isUp(end)) {
            return IntStream.range(1, start.absDeltaY(end))
                    .mapToObj(start::up)
                    .toList();
        }
        return IntStream.range(1, start.absDeltaY(end))
                .mapToObj(start::down)
                .toList();
    }
}
