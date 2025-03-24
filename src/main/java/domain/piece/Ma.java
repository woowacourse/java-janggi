package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.List;
import java.util.Map;

public class Ma extends Piece {

    private static final List<Movement> MIDDLE_MOVEMENTS = List.of(
            Movement.UP, Movement.DOWN, Movement.LEFT, Movement.RIGHT
    );

    private static final Map<Movement, List<Movement>> DESTINATIONS = Map.of(
            Movement.UP, List.of(Movement.UP_UP_LEFT, Movement.UP_UP_RIGHT),
            Movement.DOWN, List.of(Movement.DOWN_DOWN_LEFT, Movement.DOWN_DOWN_RIGHT),
            Movement.LEFT, List.of(Movement.UP_LEFT_LEFT, Movement.DOWN_LEFT_LEFT),
            Movement.RIGHT, List.of(Movement.UP_RIGHT_RIGHT, Movement.DOWN_RIGHT_RIGHT)
    );

    public Ma(Country country) {
        super(country, PieceType.MA);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate from, Board board) {
        return MIDDLE_MOVEMENTS.stream()
                .filter(middle -> !board.hasPiece(from.move(middle)))
                .flatMap(middle -> DESTINATIONS.get(middle).stream()
                        .map(from::move)
                        .filter(next -> !next.isOutOfBoundary())
                        .filter(next -> !board.hasPiece(next) || !board.isMyTeam(country, next))
                ).toList();
    }
}
