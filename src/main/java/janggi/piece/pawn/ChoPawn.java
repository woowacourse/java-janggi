package janggi.piece.pawn;

import static janggi.position.Direction.EAST;
import static janggi.position.Direction.NORTH;
import static janggi.position.Direction.SOUTH;
import static janggi.position.Direction.WEST;

import janggi.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public class ChoPawn extends Pawn{

    public ChoPawn(Position position) {
        super(position, Team.CHO);
        routes.addAll(List.of(
                new Route(List.of(EAST)),
                new Route(List.of(WEST)),
                new Route(List.of(SOUTH))
        ));
    }
}
