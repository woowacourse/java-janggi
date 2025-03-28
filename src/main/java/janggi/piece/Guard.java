package janggi.piece;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Movement;

public final class Guard extends Piece {

    private final static int MOVE_DISTANCE = 1;

    private final Board board;

    public Guard(Camp camp, Board board) {
        super(camp);
        this.board = board;
    }

    @Override
    public void validateMove(Movement movement) {
        board.validateCampPalace(movement.target(), getCamp());
        if (!isGuardMove(movement.calculateXDistance(), movement.calculateYDistance())) {
            throw new ErrorException("사는 상하좌우 또는 대각선으로 한 칸 움직여야 합니다.");
        }
    }

    private boolean isGuardMove(int xDistance, int yDistance) {
        return xDistance == MOVE_DISTANCE || yDistance == MOVE_DISTANCE;
    }

    @Override
    public Type getType() {
        return Type.GUARD;
    }
}
