package domain.place;

import domain.board.BoardView;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.Optional;

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
    public Optional<Side> getSide() {
        return Optional.empty();
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
