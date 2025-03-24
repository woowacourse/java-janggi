package janggi.piece;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Position;
import java.util.Set;

public final class Horse extends Piece {

    private final Board board;

    public Horse(Camp camp, Board board) {
        super(camp);
        this.board = board;
    }

    @Override
    public void validateMove(Position fromPosition, Position toPosition) {
        validateHorseMove(fromPosition, toPosition);
        validateObstacleOnRoute(fromPosition, toPosition);
    }

    private void validateHorseMove(Position fromPosition, Position toPosition) {
        if (!isHorseMove(fromPosition.calculateXDistance(toPosition), fromPosition.calculateYDistance(toPosition))) {
            throw new ErrorException("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
        }
    }

    private boolean isHorseMove(int xDistance, int yDistance) {
        return (xDistance == 2 && yDistance == 1) || (xDistance == 1 && yDistance == 2);
    }

    private void validateObstacleOnRoute(Position fromPosition, Position toPosition) {
        Set<Piece> pieces = board.getPiecesByPoint(Set.of(findRoute(fromPosition, toPosition)));
        if (!pieces.isEmpty()) {
            throw new ErrorException("마는 기물을 넘어서 이동할 수 없습니다.");
        }
    }

    private Position findRoute(Position fromPosition, Position toPosition) {
        if (isNextPointOnHorizontal(fromPosition, toPosition)) {
            return getNextHorizontalPoint(fromPosition, toPosition);
        }
        return getNextVerticalPoint(fromPosition, toPosition);
    }

    private boolean isNextPointOnHorizontal(Position fromPosition, Position toPosition) {
        return fromPosition.calculateXDistance(toPosition) == 2;
    }

    private Position getNextHorizontalPoint(Position fromPosition, Position toPosition) {
        if (fromPosition.getX() < toPosition.getX()) {
            return new Position(fromPosition.getX() + 1, fromPosition.getY());
        }
        return new Position(fromPosition.getX() - 1, fromPosition.getY());
    }

    private Position getNextVerticalPoint(Position fromPosition, Position toPosition) {
        if (fromPosition.getY() < toPosition.getY()) {
            return new Position(fromPosition.getX(), fromPosition.getY() + 1);
        }
        return new Position(fromPosition.getX(), fromPosition.getY() - 1);
    }

    @Override
    public Type getPieceSymbol() {
        return Type.HORSE;
    }
}
