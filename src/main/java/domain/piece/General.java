package domain.piece;

import domain.board.Position;

import java.util.List;

public class General extends Piece {

    public General(PieceColor color) {
        super(color, PieceType.GENERAL);
    }

    @Override
    public boolean isValidDestination(Position source, Position destination) {
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return List.of();
    }

    @Override
    public boolean canMove(Piece destinationPiece, List<Piece> piecesInRoute) {
        int pieceCountInRoute = this.countPieceInRoute(piecesInRoute);

        if (this.isOtherTeam(destinationPiece) && pieceCountInRoute == 0) {
            return true;
        }
        return false;
    }
}
