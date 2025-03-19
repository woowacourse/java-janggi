package domain.piece;

import domain.board.Position;

import java.util.List;

public class Cannon extends Piece {

    public Cannon(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isValidDestination(Position source, Position destination) {
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return source.getBetweenPositions(destination);
    }

    @Override
    public boolean canMove(Piece destination, List<Piece> piecesInRoute) {
        int pieceCount = this.countPieceInRoute(piecesInRoute);
        boolean noSamePiece = piecesInRoute.stream()
                .noneMatch(this::isSamePiece);

        boolean isDestinationOtherPiece = !this.isSamePiece(destination);

        if (pieceCount == 1 && this.isOtherTeam(destination) && noSamePiece && isDestinationOtherPiece) {
            return true;
        }
        return false;
    }

}
