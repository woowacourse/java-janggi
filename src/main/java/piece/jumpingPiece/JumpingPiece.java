package piece.jumpingPiece;

import piece.Piece;
import piece.Team;
import position.Board;
import position.Position;
import route.Routes;

public abstract class JumpingPiece extends Piece {

    protected JumpingPiece(Team team, Position position, Routes routes) {
        super(team, position, routes);
    }

    public Routes possibleRoutes(Board board) {
        validateTeamOfPiece(board);
        return routes.possibleJumpingRoutes(position, board);
    }
}
