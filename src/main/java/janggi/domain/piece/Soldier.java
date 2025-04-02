package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.exception.ErrorException;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;

public final class Soldier extends Piece {

    private static final int MOVE_DISTANCE = 1;

    public Soldier(Camp camp) {
        super(camp, Type.SOLDIER);
    }

    public static Piece from(Camp camp) {
        return new Soldier(camp);
    }

    @Override
    public void validateMove(Movement movement, Board board) {
        if (checkBackwardMove(movement.origin(), movement.target())) {
            throw new ErrorException("졸병은 뒤로 갈 수 없습니다.");
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
            throw new ErrorException("졸병은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }
}
