package domain.piece;

import static domain.piece.PieceType.CHA;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.ArrayList;
import java.util.List;

public class Cha extends Piece {

    private final List<Movement> movements = List.of(
            Movement.UP, Movement.DOWN, Movement.RIGHT, Movement.LEFT);

    public Cha(Country country) {
        super(country, CHA);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate currCoordinate,
                                                   Board board) {
        List<Coordinate> availablePositions = new ArrayList<>();
        for (Movement movement : movements) {
            Coordinate next = movePosition(currCoordinate, movement.getDirection());
            while (true) {
                if (board.isOutOfBoundary(next) || (board.hasPiece(next) && board.isMyTeam(
                        currCoordinate, next))) {
                    break;
                }
                if (board.hasPiece(next) && !board.isMyTeam(currCoordinate, next)) {
                    availablePositions.add(next);
                    break;
                }

                availablePositions.add(next);
                next = movePosition(next, movement.getDirection());
            }
        }
        return availablePositions;
    }

    public static Coordinate movePosition(Coordinate currCoordinate, Coordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
