package domain.place;

import domain.board.BoardView;
import domain.place.piece.Side;
import domain.position.Position;

public class Empty implements Place {

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isSameSide(Side side) {
        return false;
    }

    @Override
    public Side getSide() {
        throw new IllegalArgumentException("[ERROR] 빈칸은 진형이 없습니다.");
    }

    @Override
    public String getFormat() {
        return "．";
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
        return false;
    }
}
