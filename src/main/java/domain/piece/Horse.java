package domain.piece;

import domain.board.Position;
import java.util.List;

public class Horse extends Piece {

    public static final int HORSE_STRAIGHT_MOVE = 2;
    public static final int HORSE_SIDE_MOVE = 1;

    public Horse(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isValidDestination(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        if (Math.abs(rowDifference) == HORSE_STRAIGHT_MOVE && Math.abs(columnDifference) == HORSE_SIDE_MOVE) {
            return true;
        }
        if (Math.abs(rowDifference) == HORSE_SIDE_MOVE && Math.abs(columnDifference) == HORSE_STRAIGHT_MOVE) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        Position route = source.getPositionByFraction(destination, 2);

        return List.of(route);
    }

    @Override
    public boolean canMove(Piece destinationPiece, List<Piece> piecesInRoute) {
        int pieceCountInRoute = this.countPieceInRoute(piecesInRoute);

        return this.isOtherTeam(destinationPiece) && pieceCountInRoute == NO_PIECE;
    }
}
