package domain.piece.pathPiece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.board.PieceSearcher;
import domain.piece.Piece;
import java.util.Set;

public abstract class PathPiece extends Piece {

    public PathPiece(
        final Team team,
        final Coordinate coordinate,
        final Set<Movement> movements)
    {
        super(team, coordinate, movements);
    }

    @Override
    public boolean canMove(
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        final var path = findPath(arrival);
        final var coordinates = path.coordinates();

        return path.isReachable() && pieceSearcher.nonePiecesIn(coordinates);
    }

    protected abstract Path findPath(Coordinate arrival);
}
