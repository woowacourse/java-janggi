package janggi.piece;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Position;
import java.util.HashSet;
import java.util.Set;

public final class Elephant extends Piece {

    private final Board board;

    public Elephant(Camp camp, Board board) {
        super(camp);
        this.board = board;
    }

    @Override
    public void validateMove(Position fromPosition, Position toPosition) {
        validateElephantMove(fromPosition, toPosition);
        validateObstacleOnRoute(fromPosition, toPosition);
    }

    private void validateElephantMove(Position fromPosition, Position toPosition) {
        if (!isElephantMove(fromPosition.calculateXDistance(toPosition), fromPosition.calculateYDistance(toPosition))) {
            throw new ErrorException("상은 직선으로 한 칸, 대각선으로 두 칸 움직여야 합니다.");
        }
    }

    private boolean isElephantMove(int xDistance, int yDistance) {
        return (xDistance == 2 && yDistance == 3) || (xDistance == 3 && yDistance == 2);
    }

    private void validateObstacleOnRoute(Position fromPosition, Position toPosition) {
        Set<Piece> pieces = board.getPiecesByPosition(findRoute(fromPosition, toPosition));
        if (!pieces.isEmpty()) {
            throw new ErrorException("상은 기물을 넘어서 이동할 수 없습니다.");
        }
    }

    private Set<Position> findRoute(Position fromPosition, Position toPosition) {
        Set<Position> route = new HashSet<>();
        if (isNextPositionOnHorizontal(fromPosition, toPosition)) {
            return findHorizontalRoute(fromPosition, toPosition, route);
        }
        return findVerticalRoute(fromPosition, toPosition, route);
    }

    private boolean isNextPositionOnHorizontal(Position fromPosition, Position toPosition) {
        return fromPosition.calculateXDistance(toPosition) == 3;
    }

    private Set<Position> findHorizontalRoute(Position fromPosition, Position toPosition, Set<Position> route) {
        Position firstPosition = getNextHorizontalPosition(fromPosition, toPosition);
        route.add(firstPosition);
        route.add(findSecondPosition(toPosition, firstPosition));
        return route;
    }

    private Set<Position> findVerticalRoute(Position fromPosition, Position toPosition, Set<Position> route) {
        Position firstPosition = getNextVerticalPosition(fromPosition, toPosition);
        route.add(firstPosition);
        route.add(findSecondPosition(toPosition, firstPosition));
        return route;
    }

    private Position getNextHorizontalPosition(Position fromPosition, Position toPosition) {
        if (fromPosition.getX() < toPosition.getX()) {
            return new Position(fromPosition.getX() + 1, fromPosition.getY());
        }
        return new Position(fromPosition.getX() - 1, fromPosition.getY());
    }

    private Position getNextVerticalPosition(Position fromPosition, Position toPosition) {
        if (fromPosition.getY() < toPosition.getY()) {
            return new Position(fromPosition.getX(), fromPosition.getY() + 1);
        }
        return new Position(fromPosition.getX(), fromPosition.getY() - 1);
    }

    private Position findSecondPosition(Position toPosition, Position firstPosition) {
        return new Position((firstPosition.getX() + toPosition.getX()) / 2,
                (firstPosition.getY() + toPosition.getY()) / 2);
    }

    @Override
    public Type getPieceSymbol() {
        return Type.ELEPHANT;
    }
}
