package janggi.piece;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Movement;
import janggi.position.Position;
import java.util.Set;

public final class Horse extends Piece {

    private final Board board;

    public Horse(Camp camp, Board board) {
        super(camp);
        this.board = board;
    }

    @Override
    public void validateMove(Movement movement) {
        validateHorseMove(movement);
        validateObstacleOnRoute(movement);
    }

    private void validateHorseMove(Movement movement) {
        if (!isHorseMove(movement.calculateXDistance(), movement.calculateYDistance())) {
            throw new ErrorException("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
        }
    }

    private boolean isHorseMove(int xDistance, int yDistance) {
        return (xDistance == 2 && yDistance == 1) || (xDistance == 1 && yDistance == 2);
    }

    private void validateObstacleOnRoute(Movement movement) {
        Set<Piece> pieces = board.getPiecesByPosition(Set.of(findRoute(movement)));
        if (!pieces.isEmpty()) {
            throw new ErrorException("마는 기물을 넘어서 이동할 수 없습니다.");
        }
    }

    private Position findRoute(Movement movement) {
        if (isNextPositionOnHorizontal(movement)) {
            return getNextHorizontalPosition(movement.origin(), movement.target());
        }
        return getNextVerticalPosition(movement.origin(), movement.target());
    }

    private boolean isNextPositionOnHorizontal(Movement movement) {
        return movement.calculateXDistance() == 2;
    }

    private Position getNextHorizontalPosition(Position origin, Position target) {
        if (origin.x() < target.x()) {
            return new Position(origin.x() + 1, origin.y());
        }
        return new Position(origin.x() - 1, origin.y());
    }

    private Position getNextVerticalPosition(Position origin, Position target) {
        if (origin.y() < target.y()) {
            return new Position(origin.x(), origin.y() + 1);
        }
        return new Position(origin.x(), origin.y() - 1);
    }

    @Override
    public Type getPieceSymbol() {
        return Type.HORSE;
    }
}
