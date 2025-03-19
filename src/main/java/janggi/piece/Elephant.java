package janggi.piece;

import janggi.Side;
import janggi.board.Position;

import java.util.List;

public class Elephant implements Piece {

    private final Side side;

    public Elephant(final Side side) {
        this.side = side;
    }

    @Override
    public List<Position> computeCandidatePositions(final Position position) {
        return List.of(
                position.move(3, 2),
                position.move(3, -2),
                position.move(-3, 2),
                position.move(-3, -2),
                position.move(2, 3),
                position.move(2, -3),
                position.move(-2, 3),
                position.move(-2, -3)
        );
    }

    @Override
    public String getSymbol() {
        return "E";
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
