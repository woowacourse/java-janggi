package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.Arrays;
import java.util.List;

public class Ma extends Piece {

    private enum MaMovement {

        UP(Movement.UP, List.of(Movement.UP_UP_LEFT, Movement.UP_UP_RIGHT)),
        DOWN(Movement.DOWN, List.of(Movement.DOWN_DOWN_RIGHT, Movement.DOWN_DOWN_LEFT)),
        RIGHT(Movement.RIGHT, List.of(Movement.UP_RIGHT_RIGHT, Movement.DOWN_RIGHT_RIGHT)),
        LEFT(Movement.LEFT, List.of(Movement.UP_LEFT_LEFT, Movement.DOWN_LEFT_LEFT));

        private final Movement direction;
        private final List<Movement> destination;

        MaMovement(Movement direction, List<Movement> destination) {
            this.direction = direction;
            this.destination = destination;
        }
    }

    public Ma(Country country) {
        super(country, PieceType.MA);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate from, Board board) {
        return Arrays.stream(MaMovement.values())
                .filter(maMovement -> !board.hasPiece(from.move(maMovement.direction)))
                .flatMap(maMovement -> maMovement.destination.stream()
                        .map(from::move)
                        .filter(next -> !next.isOutOfBoundary())
                        .filter(next -> !board.hasPiece(next) || !board.isMyTeam(country, next))
                ).toList();
    }

}
