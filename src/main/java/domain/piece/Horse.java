package domain.piece;

import domain.board.Position;
import java.util.List;

public class Horse extends Piece {

    public static final int HORSE_STRAIGHT_MOVE = 2;
    public static final int HORSE_SIDE_MOVE = 1;

    public Horse(PieceColor color) {
        super(PieceType.HORSE, color);
    }

    @Override
    public boolean isValidDestination(Position source, Position destination) {
        int rowAbsDifference = Math.abs(source.rowDifference(destination));
        int columnAbsDifference = Math.abs(source.columnDifference(destination));

        return (rowAbsDifference == HORSE_STRAIGHT_MOVE && columnAbsDifference == HORSE_SIDE_MOVE)
                || (rowAbsDifference == HORSE_SIDE_MOVE && columnAbsDifference == HORSE_STRAIGHT_MOVE);
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
