package piece;

import position.Position;
import route.Routes;

public abstract class Piece {
    private final Routes routes;

    protected Piece(Routes routes) {
        this.routes = routes;
    }

    public boolean canMove(Position destination) {
        return false;
    }

    public Routes possibleRoutes() {
        return routes.possibleRoutes();
    }
}
