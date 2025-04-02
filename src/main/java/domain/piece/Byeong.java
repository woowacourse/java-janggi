package domain.piece;

import domain.piece.coordiante.Coordinate;
import domain.board.ReadableBoard;
import domain.piece.movement.Movement;
import domain.piece.movement.Movements;
import java.util.List;

public class Byeong extends Piece {

    private final Movements movements = new Movements(List.of(
            Movement.UP, Movement.DOWN, Movement.RIGHT, Movement.LEFT));

    public Byeong(Country country) {
        super(country, PieceType.BYEONG);
    }

    @Override
    public List<Coordinate> findAvailablePaths(Coordinate from, ReadableBoard readableBoard) {
        movements.addMovementIfInGung(from);

        return movements.getMovements().stream()
                .filter(this::selectUpOrDown)
                .map(from::move)
                .filter(Coordinate::isInBoundary)
                .filter(to -> !readableBoard.isMyTeam(country, to))
                .toList();
    }

    private boolean selectUpOrDown(Movement movement) {
        if (country.isCho()) {
            return isHanDirection(movement);
        }
        return isChoDirection(movement);
    }

    private boolean isHanDirection(Movement movement) {
        return movement != Movement.DOWN && movement != Movement.DOWN_RIGHT && movement != Movement.DOWN_LEFT;
    }

    private boolean isChoDirection(Movement movement) {
        return movement != Movement.UP && movement != Movement.UP_RIGHT && movement != Movement.UP_LEFT;
    }
}
