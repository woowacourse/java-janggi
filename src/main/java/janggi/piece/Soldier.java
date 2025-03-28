package janggi.piece;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Movement;
import janggi.position.Position;

public final class Soldier extends Piece {

    private static final int MOVE_DISTANCE = 1;
    private final Board board;

    public Soldier(Camp camp, Board board) {
        super(camp);
        this.board = board;
    }

    @Override
    public void validateMove(Movement movement) {
        if (checkBackwardMove(movement.origin(), movement.target())) {
            throw new ErrorException("군인은 뒤로 갈 수 없습니다.");
        }
        validateSoldierMove(movement.origin(), movement.target());
    }

    private boolean checkBackwardMove(Position origin, Position target) {
        if (getCamp().isBottom()) {
            return target.y() < origin.y();
        }
        return origin.y() < target.y();
    }

    private void validateSoldierMove(Position origin, Position target) {
        if (Math.abs(target.y() - origin.y() + origin.x() - target.x()) != MOVE_DISTANCE) {
            throw new ErrorException("군인은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }

    @Override
    public Type getType() {
        return Type.SOLDIER;
    }
}
