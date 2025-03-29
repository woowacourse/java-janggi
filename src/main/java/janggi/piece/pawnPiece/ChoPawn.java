package janggi.piece.pawnPiece;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Board;
import janggi.position.Position;
import janggi.position.Positions;
import janggi.route.Routes;
import java.util.Set;
import java.util.stream.Collectors;

public class ChoPawn extends PawnPiece {

    public ChoPawn(Position position) {
        super(Team.CHO, position, Routes.ofChoPawn());
    }

    @Override
    public PieceType type() {
        return PieceType.CHOPAWN;
    }

    @Override
    public Piece move(Team team, Position destination) {
        return new ChoPawn(destination);
    }

    @Override
    public Set<Position> possiblePalacePositions(Board board) {
        Positions palacePositions = new Positions();

        Routes palaceRoutes = palacePositions.addPossiblePalaceForChoPawnDirections(position);
        return palaceRoutes.possibleRoutes(position, board).stream()
                .filter(palacePositions::isInPalace)
                .collect(Collectors.toSet());
    }
}
