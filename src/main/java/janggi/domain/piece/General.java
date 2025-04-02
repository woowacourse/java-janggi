package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.exception.ErrorException;
import janggi.domain.position.Movement;

public final class General extends Piece {

    private final static int MOVE_DISTANCE = 1;

    public General(Camp camp) {
        super(camp, Type.GENERAL);
    }

    public static Piece from(Camp camp) {
        return new General(camp);
    }

    @Override
    public void validateMove(Movement movement, Board board) {
        board.validateCampPalace(movement.target(), getCamp());
        if (!isGeneralMove(movement.calculateXDistance(), movement.calculateYDistance())) {
            throw new ErrorException("궁은 상하좌우 또는 대각선으로 한 칸 움직여야 합니다.");
        }
    }

    private boolean isGeneralMove(int xDistance, int yDistance) {
        return xDistance == MOVE_DISTANCE || yDistance == MOVE_DISTANCE;
    }
}
