package piece.normalPiece;

import piece.Piece;
import piece.Team;
import position.Board;
import position.Position;
import route.Routes;

public abstract class NormalPiece extends Piece {

    protected NormalPiece(Team team, Position position, Routes routes) {
        super(team, position, routes);
    }

    public Routes possibleRoutes(Board board) {
        validateTeamOfPiece(board);
        return routes.possibleRoutes(position, board);
    }

}
