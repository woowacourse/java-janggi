package domain.piece;

import domain.board.Position;

import java.util.List;

public class Soldier extends Piece {

    public Soldier(PieceColor color) {
        super(color, PieceType.SOLDIER);
    }

    @Override
    public boolean isValidDestination(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        if ((color == PieceColor.RED) && (rowDifference == 1 && columnDifference == 0)) {
            return true;
        }
        if ((color == PieceColor.BLUE) && (rowDifference == -1 && columnDifference == 0)) {
            return true;
        }
        if ((rowDifference == 0 && columnDifference == -1) || (rowDifference == 0 && columnDifference == 1)) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return List.of();
    }

    @Override
    public boolean canMove(Piece destinationPiece, List<Piece> piecesInRoute) {
        int pieceCountInRoute = this.countPieceInRoute(piecesInRoute);

        return this.isOtherTeam(destinationPiece) && pieceCountInRoute == 0;
    }
}
