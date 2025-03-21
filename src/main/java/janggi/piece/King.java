package janggi.piece;

import janggi.board.Direction;
import janggi.board.Position;
import janggi.board.Route;
import java.util.List;

public class King extends Piece {

    public King(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        return List.of(
                new Route(position.move(Direction.LEFT)),
                new Route(position.move(Direction.RIGHT)),
                new Route(position.move(Direction.UP)),
                new Route(position.move(Direction.DOWN))
        );
    }

    @Override
    public String getSymbol() {
        return "G";
    }

}
