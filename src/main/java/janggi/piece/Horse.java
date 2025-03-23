package janggi.piece;

import static janggi.position.Direction.EAST;
import static janggi.position.Direction.EAST_NORTH;
import static janggi.position.Direction.EAST_SOUTH;
import static janggi.position.Direction.NORTH;
import static janggi.position.Direction.NORTH_EAST;
import static janggi.position.Direction.NORTH_WEST;
import static janggi.position.Direction.SOUTH;
import static janggi.position.Direction.SOUTH_EAST;
import static janggi.position.Direction.SOUTH_WEST;
import static janggi.position.Direction.WEST;
import static janggi.position.Direction.WEST_NORTH;
import static janggi.position.Direction.WEST_SOUTH;

import janggi.Team;
import janggi.position.Direction;
import janggi.position.Route;
import java.util.List;

import janggi.position.Position;

public class Horse extends Piece {

    public Horse(Position position, Team team) {
        super(position, team);
        routes.addAll(List.of(
                new Route(List.of(NORTH, NORTH_EAST)),
                new Route(List.of(NORTH, NORTH_WEST)),

                new Route(List.of(SOUTH, SOUTH_EAST)),
                new Route(List.of(SOUTH, SOUTH_WEST)),

                new Route(List.of(WEST, WEST_NORTH)),
                new Route(List.of(WEST, WEST_SOUTH)),

                new Route(List.of(EAST, EAST_NORTH)),
                new Route(List.of(EAST, EAST_SOUTH))
        ));
    }

    @Override
    public PieceType type() {
        return PieceType.HORSE;
    }
}
