package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.List;

public class Sa extends Piece {

    private static final List<Movement> MOVEMENTS = List.of(
            Movement.UP, Movement.UP_RIGHT, Movement.RIGHT, Movement.DOWN_RIGHT,
            Movement.DOWN, Movement.DOWN_LEFT, Movement.LEFT, Movement.UP_LEFT
    );

    public Sa(Country country) {
        super(country, PieceType.SA);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate from, Board board) {
        return MOVEMENTS.stream()
                .map(from::move)
                .filter(to -> !to.isOutOfBoundary())
                .filter(to -> !board.isMyTeam(country, to))
                .toList();
    }

}
