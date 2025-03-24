package janggi.piece;

import janggi.board.Position;
import java.util.List;
import java.util.Map;

public class Cannon extends Piece {

    public Cannon(final Side side) {
        super(side);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Map<Position, Piece> board) {
        List<Position> path = findPath(start, end);
        return isOnePieceOnPath(board, path)
                && notExistsCannonOnPath(board, path)
                && isNotCannonTargetPiece(board, end)
                && isValidMovingRule(start, end);
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    private boolean isOnePieceOnPath(final Map<Position, Piece> board, final List<Position> path) {
        long countPieceOnPath = path
                .stream()
                .filter(board::containsKey)
                .count();
        return countPieceOnPath == 1;
    }

    private boolean notExistsCannonOnPath(final Map<Position, Piece> board, final List<Position> path) {
        return path.stream().noneMatch(position -> board.containsKey(position) && board.get(position).isCannon());
    }

    private boolean isNotCannonTargetPiece(final Map<Position, Piece> board, final Position end) {
        return !(board.containsKey(end) && board.get(end).isCannon());
    }

    private boolean isValidMovingRule(final Position start, final Position end) {
        return start.isHorizontalMove(end) || start.isVerticalMove(end);
    }

    private List<Position> findPath(final Position start, final Position end) {
        if (start.isHorizontalMove(end)) {
            return start.horizontalPath(end);
        }
        return start.verticalPath(end);
    }
}
