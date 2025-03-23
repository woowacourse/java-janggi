package domain.piece;

import domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {

    public static final int ELEPHANT_STRAIGHT_MOVE = 3;
    public static final int ELEPHANT_SIDE_MOVE = 2;

    public Elephant(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isValidDestination(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        if (Math.abs(rowDifference) == ELEPHANT_STRAIGHT_MOVE && Math.abs(columnDifference) == ELEPHANT_SIDE_MOVE) {
            return true;
        }
        if (Math.abs(rowDifference) == 2 && Math.abs(columnDifference) == 3) {
            return true;
        }
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        List<Position> positions = new ArrayList<>();

        Position firstPosition = source.getPositionByFraction(destination, 3);
        Position secondPosition = firstPosition.getPositionByFraction(destination, 2);

        positions.add(firstPosition);
        positions.add(secondPosition);
        return positions;
    }

    @Override
    public boolean canMove(Piece destinationPiece, List<Piece> piecesInRoute) {
        int pieceCountInRoute = this.countPieceInRoute(piecesInRoute);

        return this.isOtherTeam(destinationPiece) && pieceCountInRoute == NO_PIECE;
    }
}
