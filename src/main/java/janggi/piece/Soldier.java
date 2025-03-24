package janggi.piece;

import janggi.position.Position;
import janggi.board.Board;
import janggi.exception.ErrorException;

public final class Soldier extends Piece {

    private final Board board;

    public Soldier(Camp camp, Board board) {
        super(camp);
        this.board = board;
    }

    @Override
    public void validateMove(Position fromPosition, Position toPosition) {
        if (isStart()) {
            validateJolMove(fromPosition, toPosition);
            return;
        }
        validateByeongMove(fromPosition, toPosition);
    }

    private void validateJolMove(Position fromPosition, Position toPosition) {
        if (toPosition.getY() < fromPosition.getY()) {
            throw new ErrorException("졸은 뒤로 갈 수 없습니다.");
        }
        if (Math.abs(toPosition.getY() - fromPosition.getY() + fromPosition.getX() - toPosition.getX()) != 1) {
            throw new ErrorException("졸은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }

    private void validateByeongMove(Position fromPosition, Position toPosition) {
        if (fromPosition.getY() < toPosition.getY()) {
            throw new ErrorException("병은 뒤로 갈 수 없습니다.");
        }
        if (Math.abs(toPosition.getY() - fromPosition.getY() + fromPosition.getX() - toPosition.getX()) != 1) {
            throw new ErrorException("병은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }

    @Override
    public Type getPieceSymbol() {
        return Type.SOLDIER;
    }
}
