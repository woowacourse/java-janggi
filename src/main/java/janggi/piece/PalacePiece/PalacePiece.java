package janggi.piece.PalacePiece;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Board;
import janggi.position.Position;
import janggi.route.Routes;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class PalacePiece extends Piece {
    private final Position position;
    private final Routes routes;

    public PalacePiece(Team team, Position position, Routes routes) {
        super(team);
        this.position = position;
        this.routes = routes;
    }

    public Set<Position> possibleRoutes(Board board) {
//        board.validateTeam(team());
//        return routes.possibleRoutes(position, board).stream()
//                .filter(position1 -> position1.isInPalace())
//                .collect(Collectors.toSet());
        return null;
    }

    public Position position() {
        return position;
    }
}
