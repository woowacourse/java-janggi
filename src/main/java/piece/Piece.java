package piece;

import position.Board;
import position.Position;
import route.Routes;

public abstract class Piece {
    protected final Team team;
    protected final Position position;
    protected final Routes routes;

    protected Piece(Team team, Position position, Routes routes) {
        this.team = team;
        this.position = position;
        this.routes = routes;
    }

    public abstract Routes possibleRoutes(Board board);

//    public Routes possibleRoutes(Board board) {
//        validateTeamOfPiece(board);
//        return routes.possibleRoutes(position, board);
//    }

    protected void validateTeamOfPiece(Board board) {
        board.validateTeam(team);
    }

    public abstract PieceType type();

    public Position position() {
        return position;
    }

    public boolean isDifferentTeam(Team currentTeam) {
        return team!=currentTeam;
    }
}
