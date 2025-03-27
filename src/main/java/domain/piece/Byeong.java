package domain.piece;

import domain.Coordinate;
import domain.board.Board;
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
    public List<Coordinate> findAvailablePaths(Coordinate from, Board board) {
        movements.addMovementIfInGung(from);

        return movements.getMovements().stream()
                .filter(this::selectUpOrDown)
                .map(from::move)
                .filter(Coordinate::isInBoundary)
                .filter(to -> !board.isMyTeam(country, to))
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
