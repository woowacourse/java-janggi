package domain.piece;

import domain.board.Position;

import java.util.List;

public class Chariot extends Piece {
    public Chariot(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isValidDestination(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        return rowDifference == NO_MOVE || columnDifference == NO_MOVE;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return source.getBetweenPositions(destination);
    }

    @Override
    public boolean canMove(Piece destinationPiece, List<Piece> piecesInRoute) {
        int pieceCountInRoute = this.countPieceInRoute(piecesInRoute);

        return this.isOtherTeam(destinationPiece) && pieceCountInRoute == NO_PIECE;
    }
}
