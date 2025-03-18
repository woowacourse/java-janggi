package janggi.piece;

import janggi.Side;
import janggi.board.Position;

import java.util.List;

public class Soldier implements Piece {

    private final Side side;

    public Soldier(final Side side) {
        this.side = side;
    }

    @Override
    public List<Position> computeCandidatePositions(Position position) {
        if (isCho()) {
            return moveCho(position);
        }
        return moveHan(position);
    }

    private static List<Position> moveCho(final Position position) {
        return List.of(
                position.move(-1, 0),
                position.move(1, 0),
                position.move(0, -1)
        );
    }

    private static List<Position> moveHan(final Position position) {
        return List.of(
                position.move(-1, 0),
                position.move(1, 0),
                position.move(0, 1)
        );
    }

    @Override
    public String getSymbol() {
        return "J";
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
