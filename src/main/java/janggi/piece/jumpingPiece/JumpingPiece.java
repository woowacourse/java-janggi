package janggi.piece.jumpingPiece;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Board;
import janggi.position.Position;
import janggi.route.Routes;
import java.util.Set;

public abstract class JumpingPiece extends Piece {

    protected JumpingPiece(Team team, Position position, Routes routes) {
        super(team, position, routes);
    }

    public Set<Position> possibleRoutes(Board board) {
        validateTeamOfPiece(board);
        return routes.possibleJumpingRoutes(position, board);
    }
}
