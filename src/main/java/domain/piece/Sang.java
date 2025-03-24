package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.ArrayList;
import java.util.List;

public class Sang extends Piece {

    public enum SangMovement {

        UP(Movement.UP,
                List.of(Movement.UP_UP_RIGHT, Movement.UP_UP_UP_RIGHT_RIGHT),
                List.of(Movement.UP_UP_LEFT, Movement.UP_UP_UP_LEFT_LEFT)),

        DOWN(Movement.DOWN,
                List.of(Movement.DOWN_DOWN_RIGHT, Movement.DOWN_DOWN_DOWN_RIGHT_RIGHT),
                List.of(Movement.DOWN_DOWN_LEFT, Movement.DOWN_DOWN_DOWN_LEFT_LEFT)),

        RIGHT(Movement.RIGHT,
                List.of(Movement.UP_RIGHT_RIGHT, Movement.DOWN_RIGHT_RIGHT),
                List.of(Movement.UP_LEFT_LEFT, Movement.DOWN_LEFT_LEFT)),

        LEFT(Movement.LEFT,
                List.of(Movement.UP_RIGHT_RIGHT, Movement.DOWN_RIGHT_RIGHT),
                List.of(Movement.UP_LEFT_LEFT, Movement.DOWN_LEFT_LEFT));

        private final Movement direction;
        private final List<Movement> leftDestination;
        private final List<Movement> rightDestination;

        SangMovement(Movement direction, List<Movement> leftDestination, List<Movement> rightDestination) {
            this.direction = direction;
            this.leftDestination = leftDestination;
            this.rightDestination = rightDestination;
        }
    }

    public Sang(Country country) {
        super(country, PieceType.SANG);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate from,
                                                   Board board) {
        List<Coordinate> availablePositions = new ArrayList<>();
        for (SangMovement sangMovement : SangMovement.values()) {
            Coordinate next = from.move(sangMovement.direction);
            if (!board.hasPiece(next)) {
                List<Movement> left = sangMovement.leftDestination;
                if (!board.hasPiece(from.move(left.get(0)))
                        && (!board.hasPiece(from.move(left.get(1)))
                        || !board.isMyTeam(country, from.move(left.get(1))))) {
                    availablePositions.add(from.move(left.get(1)));
                }

                List<Movement> right = sangMovement.rightDestination;
                if (!board.hasPiece(from.move(right.get(0))) &&
                        (!board.hasPiece(from.move(right.get(1))) || !board.isMyTeam(
                                country, from.move(right.get(1))))) {
                    availablePositions.add(from.move(right.get(1)));
                }
            }
        }
        return availablePositions;
    }
}
