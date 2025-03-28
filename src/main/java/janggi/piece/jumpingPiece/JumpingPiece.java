package janggi.piece.jumpingPiece;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Board;
import janggi.position.Position;
import janggi.position.Positions;
import janggi.route.Routes;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class JumpingPiece extends Piece {
    private final Position position;
    private final Routes routes;

    protected JumpingPiece(Team team, Position position, Routes routes) {
        super(team);
        this.position = position;
        this.routes = routes;
    }

    public Set<Position> possibleRoutes(Board board) {
        board.validateTeam(team());
        Set<Position> positions = routes.possibleJumpingRoutes(position, board);
        Set<Position> palacePositions = possiblePalacePositions(board);

        return Stream.concat(positions.stream(), palacePositions.stream())
                .collect(Collectors.toSet());
    }

    public Set<Position> possiblePalacePositions(Board board) {
        Positions palacePositions = new Positions();

        Routes palaceRoutes = palacePositions.addPossiblePalaceDirections(position);
        return palaceRoutes.possibleJumpingRoutes(position, board).stream()
                .filter(palacePositions::isInPalace)
                .collect(Collectors.toSet());
    }

    public Position position() {
        return position;
    }
}
