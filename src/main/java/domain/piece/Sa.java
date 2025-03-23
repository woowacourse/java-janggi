package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.List;

public class Sa extends Piece {

    private final List<Movement> movements = List.of(
            Movement.UP, Movement.UP_RIGHT, Movement.RIGHT, Movement.DOWN_RIGHT,
            Movement.DOWN, Movement.DOWN_LEFT, Movement.LEFT, Movement.UP_LEFT);

    public Sa(Country country) {
        super(country, PieceType.SA);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate currCoordinate,
                                                   Board board) {
        return movements.stream()
                .map(movement -> movePosition(currCoordinate, movement.getDirection()))
                .filter(next -> !board.isOutOfBoundary(next) && !board.isMyTeam(currCoordinate, next))
                .toList();
    }

    public static Coordinate movePosition(Coordinate currCoordinate, Coordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
