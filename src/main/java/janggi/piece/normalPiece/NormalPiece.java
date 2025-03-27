package janggi.piece.normalPiece;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Board;
import janggi.position.Position;
import janggi.route.Routes;
import java.util.Set;

public abstract class NormalPiece extends Piece {
    private final Position position;
    private final Routes routes;

    protected NormalPiece(Team team, Position position, Routes routes) {
        super(team);
        this.position = position;
        this.routes = routes;
    }

    public Set<Position> possibleRoutes(Board board) {
        board.validateTeam(team());
        return routes.possibleRoutes(position, board);
    }

    public Position position() {
        return position;
    }
}
