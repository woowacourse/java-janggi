package piece.straightPiece;

import piece.Piece;
import piece.Team;
import position.Board;
import position.Position;
import route.Routes;

public abstract class StraightPiece extends Piece {

    protected StraightPiece(Team team, Position position, Routes routes) {
        super(team, position, routes);
    }

    public Routes possibleRoutes(Board board) {
        validateTeamOfPiece(board);
        return routes.possibleStraightRoutes(position, board);
    }
}
