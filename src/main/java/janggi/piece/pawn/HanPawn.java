package janggi.piece.pawn;

import static janggi.position.Direction.EAST;
import static janggi.position.Direction.NORTH;
import static janggi.position.Direction.SOUTH;
import static janggi.position.Direction.WEST;

import janggi.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public class HanPawn extends Pawn{

    public HanPawn(Position position) {
        super(position, Team.HAN);
        routes.addAll(List.of(
                new Route(List.of(EAST)),
                new Route(List.of(WEST)),
                new Route(List.of(NORTH))
        ));
    }
}
