package piece;

import position.Board;
import position.Position;
import route.Routes;

public abstract class Piece {
    private final Team team;
    private final Position position;
    private final Routes routes;

    protected Piece(Team team, Position position, Routes routes) {
        this.team = team;
        this.position = position;
        this.routes = routes;
    }

    public Routes possibleRoutes(Board board) {
        validateTeamOfPiece(board);
        return routes.possibleRoutes(position, board);
    }

    private void validateTeamOfPiece(Board board) {
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
