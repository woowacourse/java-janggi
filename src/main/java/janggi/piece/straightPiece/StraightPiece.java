package janggi.piece.straightPiece;

import java.util.Set;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Board;
import janggi.position.Position;
import janggi.route.Routes;

public abstract class StraightPiece extends Piece {

    protected StraightPiece(Team team, Position position, Routes routes) {
        super(team, position, routes);
    }

    public Set<Position> possibleRoutes(Board board) {
        validateTeamOfPiece(board);
        return routes.possibleStraightRoutes(position, board);
    }
}
