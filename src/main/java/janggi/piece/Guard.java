package janggi.piece;

import janggi.board.Position;
import janggi.board.Route;

import java.util.List;

public class Guard implements Piece {

    private final Side side;

    public Guard(final Side side) {
        this.side = side;
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        return List.of(
                new Route(position.move(-1, 0)),
                new Route(position.move(1, 0)),
                new Route(position.move(0, -1)),
                new Route(position.move(0, 1))
        );
    }

    @Override
    public String getSymbol() {
        return "S";
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
