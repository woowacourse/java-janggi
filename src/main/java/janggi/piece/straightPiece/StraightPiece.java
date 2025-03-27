package janggi.piece.straightPiece;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Board;
import janggi.position.Position;
import janggi.route.Routes;
import java.util.Set;

public abstract class StraightPiece extends Piece {
    private final Position position;
    private final Routes routes;

    protected StraightPiece(Team team, Position position, Routes routes) {
        super(team);
        this.position = position;
        this.routes = routes;
    }

    public Set<Position> possibleRoutes(Board board) {
        board.validateTeam(team());
        return routes.possibleStraightRoutes(position, board);
    }

    public Position position() {
        return position;
    }
}
