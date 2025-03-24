package piece;

import position.Board;
import position.Position;
import route.Routes;

public abstract class Piece {
    private final Team team;
    private final Position position;
    private final Routes routes;

    protected Piece(Team team, Position position, Routes routes) {
        this.team = team;
        this.position = position;
        this.routes = routes;
    }

    public Routes possibleRoutes(Board board) {
        return routes.possibleRoutes(position, board);
    }

    public abstract PieceType type();

    public Position position() {
        return position;
    }
}
