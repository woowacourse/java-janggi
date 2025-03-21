package janggi.piece;

import janggi.board.Position;
import janggi.board.Route;
import java.util.List;

public class Soldier extends Piece {

    public Soldier(final Side side) {
        super(side);
    }

    @Override
    public List<Route> computeCandidatePositions(Position position) {
        if (isCho()) {
            return moveCho(position);
        }
        return moveHan(position);
    }

    @Override
    public String getSymbol() {
        return "J";
    }

    private List<Route> moveCho(final Position position) {
        return List.of(
                new Route(position.move(-1, 0)),
                new Route(position.move(1, 0)),
                new Route(position.move(0, -1))
        );
    }

    private List<Route> moveHan(final Position position) {
        return List.of(
                new Route(position.move(-1, 0)),
                new Route(position.move(1, 0)),
                new Route(position.move(0, 1))
        );
    }

}
