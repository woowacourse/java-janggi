package piece;

import java.util.Set;
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

    public abstract Set<Position> possibleRoutes(Board board);

    protected void validateTeamOfPiece(Board board) {
        board.validateTeam(team);
    }

    public abstract PieceType type();

    public Position position() {
        return position;
    }

    public boolean isDifferentTeam(Team currentTeam) {
        return team != currentTeam;
    }
}
