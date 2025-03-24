package janggi.piece;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Position;
import java.util.HashSet;
import java.util.Set;

public final class Chariot extends Piece {

    private final Board board;

    public Chariot(Camp camp, Board board) {
        super(camp);
        this.board = board;
    }

    @Override
    public void validateMove(Position fromPosition, Position toPosition) {
        validateLinearMove(fromPosition, toPosition);
        validateObstacleOnRoute(fromPosition, toPosition);
    }

    private void validateLinearMove(Position fromPosition, Position toPosition) {
        if (!fromPosition.isHorizontal(toPosition) && !fromPosition.isVertical(toPosition)) {
            throw new ErrorException("차는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    private void validateObstacleOnRoute(Position fromPosition, Position toPosition) {
        Set<Piece> pieces = board.getPiecesByPoint(findRoute(fromPosition, toPosition));
        if (!pieces.isEmpty()) {
            throw new ErrorException("차는 기물을 넘어 이동할 수 없습니다.");
        }
    }

    private Set<Position> findRoute(Position fromPosition, Position toPosition) {
        boolean isHorizontal = fromPosition.isHorizontal(toPosition);
        if (isHorizontal) {
            return findHorizontalRoute(fromPosition.getY(), fromPosition.getX(), toPosition.getX());
        }
        return findVerticalRoute(fromPosition.getX(), fromPosition.getY(), toPosition.getY());
    }

    private Set<Position> findHorizontalRoute(int fixedY, int fromX, int toX) {
        Set<Position> route = new HashSet<>();
        int start = Math.min(fromX, toX) + 1;
        int end = Math.max(fromX, toX);
        for (int i = start; i < end; i++) {
            route.add(new Position(i, fixedY));
        }
        return route;
    }

    private Set<Position> findVerticalRoute(int fixedX, int fromY, int toY) {
        Set<Position> route = new HashSet<>();
        int start = Math.min(fromY, toY) + 1;
        int end = Math.max(fromY, toY);
        for (int i = start; i < end; i++) {
            route.add(new Position(fixedX, i));
        }
        return route;
    }

    @Override
    public Type getPieceSymbol() {
        return Type.CHARIOT;
    }
}
