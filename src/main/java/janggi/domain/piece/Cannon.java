package janggi.domain.piece;

import janggi.domain.board.Position;

import java.util.List;

public class Cannon extends Piece {

    public Cannon(PieceColor color) {
        super(color, PieceType.CANNON);
    }

    @Override
    public boolean isValidDestination(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        return rowDifference == 0 || columnDifference == 0;
    }
    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return source.getBetweenPositions(destination);
    }

    @Override
    public boolean canMove(Piece destination, List<Piece> piecesInRoute) {
        int pieceCount = this.countPieceInRoute(piecesInRoute);
        boolean noSamePiece = piecesInRoute.stream()
                .noneMatch(this::isSamePieceType);

        boolean isDestinationOtherPiece = !this.isSamePieceType(destination);

        return pieceCount == 1 && this.isOtherTeam(destination) && noSamePiece && isDestinationOtherPiece;
    }

}
