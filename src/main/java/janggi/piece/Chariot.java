package janggi.piece;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Movement;
import janggi.position.Position;
import java.util.Set;

public final class Chariot extends Piece {

    private final Board board;

    public Chariot(Camp camp, Board board) {
        super(camp);
        this.board = board;
    }

    @Override
    public void validateMove(Movement movement) {
        validateLinearMove(movement);
        validateObstacleOnRoute(movement);
    }

    private void validateLinearMove(Movement movement) {
        if (!movement.isHorizontal() && !movement.isVertical()) {
            throw new ErrorException("차는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    private void validateObstacleOnRoute(Movement movement) {
        Set<Piece> pieces = board.getPiecesByPosition(findRoute(movement));
        if (!pieces.isEmpty()) {
            throw new ErrorException("차는 기물을 넘어 이동할 수 없습니다.");
        }
    }

    private Set<Position> findRoute(Movement movement) {
        return movement.findRoute();
    }

    @Override
    public Type getType() {
        return Type.CHARIOT;
    }
}
