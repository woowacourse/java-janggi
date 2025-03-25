package janggi.piece.normalPiece;

import java.util.Set;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Board;
import janggi.position.Position;
import janggi.route.Routes;

public abstract class NormalPiece extends Piece {

    protected NormalPiece(Team team, Position position, Routes routes) {
        super(team, position, routes);
    }

    public Set<Position> possibleRoutes(Board board) {
        validateTeamOfPiece(board);
        return routes.possibleRoutes(position, board);
    }
}
