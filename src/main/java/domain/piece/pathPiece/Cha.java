package domain.piece.pathPiece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.board.PieceSearcher;
import domain.piece.Piece;

public class Cha extends UnlimitedPathPiece {

    public Cha(Team team, Coordinate coordinate) {
        super(
            team,
            coordinate,
            Movement.CROSS_MOVEMENTS
        );
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

    @Override
    public Piece moveTo(final Coordinate arrival) {
        return new Cha(team, arrival);
    }
}
