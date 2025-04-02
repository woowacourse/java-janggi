package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.exception.ErrorException;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import java.util.Set;

public final class Chariot extends Piece {

    public Chariot(Camp camp) {
        super(camp, Type.CHARIOT);
    }

    public static Piece from(Camp camp) {
        return new Chariot(camp);
    }

    @Override
    public void validateMove(Movement movement, Board board) {
        validateLinearMove(movement);
        validateObstacleOnRoute(movement, board);
    }

    private void validateLinearMove(Movement movement) {
        if (!movement.isHorizontal() && !movement.isVertical()) {
            throw new ErrorException("차는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    private void validateObstacleOnRoute(Movement movement, Board board) {
        Set<Piece> pieces = board.getPiecesByPosition(findRoute(movement));
        if (!pieces.isEmpty()) {
            throw new ErrorException("차는 기물을 넘어 이동할 수 없습니다.");
        }
    }

    private Set<Position> findRoute(Movement movement) {
        return movement.findRoute();
    }
}
