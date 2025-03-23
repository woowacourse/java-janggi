package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.List;

public class Byeong extends Piece {

    private final List<Movement> MOVEMENTS = List.of(
            Movement.UP, Movement.DOWN, Movement.RIGHT, Movement.LEFT);

    public Byeong(Country country) {
        super(country, PieceType.BYEONG);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate currCoordinate, Board board) {
        return MOVEMENTS.stream()
                .filter(this::getForwardDirection)
                .map(movement -> currCoordinate.move(
                        movement.getDirection().getRow(),
                        movement.getDirection().getCol()))
                .filter(next -> !board.isOutOfBoundary(next) && !board.isMyTeam(currCoordinate, next))
                .toList();
    }

    private boolean getForwardDirection(Movement movement) {
        if (country.isCho()) {
            return movement != Movement.DOWN;
        } else {
            return movement != Movement.UP;
        }
    }
}
