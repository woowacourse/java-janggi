package piece;

import position.Board;
import position.Position;
import route.Routes;

public abstract class Piece {
    private final Position position;
    private final Routes routes;

    protected Piece(Position position, Routes routes) {
        this.position = position;
        this.routes = routes;
    }

    public boolean canMove(Position destination) {
        return false;
    }

    public Routes possibleRoutes(Board board) {
        return routes.possibleRoutes(position, board);
    }

    public abstract PieceType type();

    public Position position() {
        return position;
    }
}
