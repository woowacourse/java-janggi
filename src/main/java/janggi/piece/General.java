package janggi.piece;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Movement;

public final class General extends Piece {

    private final Board board;

    public General(Camp camp, Board board) {
        super(camp);
        this.board = board;
    }

    @Override
    public void validateMove(Movement movement) {
        board.validateCampPalace(movement.target(), getCamp());
        if (!isGeneralMove(movement.calculateXDistance(), movement.calculateYDistance())) {
            throw new ErrorException("궁은 상하좌우 또는 대각선으로 한 칸 움직여야 합니다.");
        }
    }

    private boolean isGeneralMove(int xDistance, int yDistance) {
        return xDistance == 1 || yDistance == 1;
    }

    @Override
    public Type getPieceSymbol() {
        return Type.GENERAL;
    }
}
