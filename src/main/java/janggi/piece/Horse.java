package janggi.piece;

import janggi.Side;
import janggi.board.Position;

import java.util.List;

public class Horse implements Piece {

    private final Side side;

    public Horse(final Side side) {
        this.side = side;
    }

    @Override
    public List<Position> computeCandidatePositions(final Position position) {
        return List.of(
                position.move(2, 1),
                position.move(2, -1),
                position.move(-2, 1),
                position.move(-2, -1),
                position.move(1, 2),
                position.move(1, -2),
                position.move(-1, 2),
                position.move(-1, -2)
        );
    }

    @Override
    public String getSymbol() {
        return "M";
    }

    @Override
    public boolean isCho() {
        return side == Side.CHO;
    }

    @Override
    public boolean isHan() {
        return side == Side.HAN;
    }
}
