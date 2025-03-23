package janggi.piece;

import static janggi.position.Direction.EAST;
import static janggi.position.Direction.NORTH;
import static janggi.position.Direction.SOUTH;
import static janggi.position.Direction.WEST;

import janggi.Team;
import janggi.position.Route;
import java.util.List;

import janggi.position.Position;

public class Palace extends Piece {

    public Palace(Position position, Team team) {
        super(position, team);
        routes.addAll(List.of(
                new Route(List.of(EAST)),
                new Route(List.of(WEST)),
                new Route(List.of(SOUTH)),
                new Route(List.of(NORTH))
        ));
    }

    @Override
    public PieceType type() {
        return PieceType.PALACE;
    }
}
