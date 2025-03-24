package domain.piece.noPathPiece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.board.PieceSearcher;
import java.util.Set;

public abstract class InCastleNoPathPiece extends NoPathPiece {

    public InCastleNoPathPiece(final Team team, final Coordinate coordinate,
        final Set<Movement> movements) {
        super(team, coordinate, movements);
    }

    @Override
    public boolean canMove(final Coordinate arrival, final PieceSearcher pieceSearcher) {
        return arrival.isInCastle() && super.canMove(arrival, pieceSearcher);
    }
}
