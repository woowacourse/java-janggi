package domain.piece.noPathPiece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.board.PieceSearcher;
import domain.piece.Piece;
import java.util.Set;

public abstract class NoPathPiece extends Piece {

    public NoPathPiece(final Team team, final Coordinate coordinate, final Set<Movement> movements) {
        super(team, coordinate, movements);
    }

    @Override
    public boolean canMove(final Coordinate arrival, final PieceSearcher pieceSearcher) {
        return movements().stream()
            .filter(coordinate::canMove)
            .map(coordinate::move)
            .anyMatch(arrival::equals);
    }
}
