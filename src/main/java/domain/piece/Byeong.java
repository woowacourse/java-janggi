package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.List;

public class Byeong extends Piece {

    private static final List<Movement> MOVEMENTS = List.of(
            Movement.UP, Movement.DOWN, Movement.RIGHT, Movement.LEFT);

    public Byeong(Country country) {
        super(country, PieceType.BYEONG);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate from, Board board) {
        return MOVEMENTS.stream()
                .filter(this::selectUpOrDown)
                .map(from::move)
                .filter(to -> !to.isOutOfBoundary())
                .filter(to -> !board.isMyTeam(country, to))
                .toList();
    }

    private boolean selectUpOrDown(Movement movement) {
        if (country.isCho()) {
            return movement != Movement.DOWN;
        }
        return movement != Movement.UP;
    }
}
