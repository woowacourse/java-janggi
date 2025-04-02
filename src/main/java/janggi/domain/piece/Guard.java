package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.exception.ErrorException;
import janggi.domain.position.Movement;

public final class Guard extends Piece {

    private final static int MOVE_DISTANCE = 1;

    public Guard(Camp camp) {
        super(camp, Type.GUARD);
    }

    public static Piece from(Camp camp) {
        return new Guard(camp);
    }

    @Override
    public void validateMove(Movement movement, Board board) {
        board.validateCampPalace(movement.target(), getCamp());
        if (!isGuardMove(movement.calculateXDistance(), movement.calculateYDistance())) {
            throw new ErrorException("사는 상하좌우 또는 대각선으로 한 칸 움직여야 합니다.");
        }
    }

    private boolean isGuardMove(int xDistance, int yDistance) {
        return xDistance <= MOVE_DISTANCE && yDistance <= MOVE_DISTANCE && !(xDistance == 0 && yDistance == 0);
    }
}
