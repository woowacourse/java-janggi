package janggi.piece.pawnPiece;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Board;
import janggi.position.Position;
import janggi.route.Routes;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class PawnPiece extends Piece {
    protected final Position position;
    private final Routes routes;

    protected PawnPiece(Team team, Position position, Routes routes) {
        super(team);
        this.position = position;
        this.routes = routes;
    }

    public Set<Position> possibleRoutes(Board board) {
        board.validateTeam(team());
        Set<Position> positions = routes.possibleRoutes(position, board);
        Set<Position> palacePositions = possiblePalacePositions(board);

        return Stream.concat(positions.stream(), palacePositions.stream())
                .collect(Collectors.toSet());
    }

    public Position position() {
        return position;
    }
}
