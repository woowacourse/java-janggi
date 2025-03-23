package domain.piece;

import domain.board.Position;

import java.util.List;

public class Soldier extends Piece {

    public static final int FORWARD_MOVE = 1;
    public static final int BACKWARD_MOVE = -1;

    public Soldier(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isValidDestination(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        if ((color == PieceColor.RED) && (rowDifference == FORWARD_MOVE && columnDifference == NO_MOVE)) {
            return true;
        }
        if ((color == PieceColor.BLUE) && (rowDifference == BACKWARD_MOVE && columnDifference == NO_MOVE)) {
            return true;
        }
        if ((rowDifference == NO_MOVE && columnDifference == BACKWARD_MOVE) || (rowDifference == NO_MOVE && columnDifference == FORWARD_MOVE)) {
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

        return this.isOtherTeam(destinationPiece) && pieceCountInRoute == NO_PIECE;
    }
}
