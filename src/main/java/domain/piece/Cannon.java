package domain.piece;

import domain.board.Position;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(PieceColor color) {
        super(PieceType.CANNON, color);
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
    public boolean canMove(Piece destination, List<Piece> piecesInRoute) {
        int pieceCount = this.countPieceInRoute(piecesInRoute);
        if (pieceCount != ONE_PIECE) {
            return false;
        }

        boolean hasSamePiece = piecesInRoute.stream()
                .anyMatch(this::isSamePieceType);
        if (hasSamePiece) {
            return false;
        }

        boolean isDestinationSamePiece = this.isSamePieceType(destination);
        if (isDestinationSamePiece) {
            return false;
        }

        return this.isOtherTeam(destination);
    }
}
