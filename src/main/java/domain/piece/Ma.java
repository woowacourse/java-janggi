package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.ArrayList;
import java.util.List;

public class Ma extends Piece {

    private static final List<List<Movement>> MOVEMENTS = List.of(
            List.of(Movement.UP, Movement.UP_UP_LEFT),
            List.of(Movement.UP, Movement.UP_UP_RIGHT),
            List.of(Movement.DOWN, Movement.DOWN_DOWN_LEFT),
            List.of(Movement.DOWN, Movement.DOWN_DOWN_RIGHT),
            List.of(Movement.LEFT, Movement.UP_LEFT_LEFT),
            List.of(Movement.LEFT, Movement.DOWN_LEFT_LEFT),
            List.of(Movement.RIGHT, Movement.UP_RIGHT_RIGHT),
            List.of(Movement.RIGHT, Movement.DOWN_RIGHT_RIGHT)
    );

    public Ma(Country country) {
        super(country, PieceType.MA);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate from, Board board) {
        List<Coordinate> availables = new ArrayList<>();

        for (List<Movement> movement : MOVEMENTS) {
            Coordinate first = from.move(movement.get(0));
            Coordinate to = from.move(movement.get(1));

            if (first.isOutOfBoundary() || to.isOutOfBoundary()) {
                continue;
            }
            if (board.hasPiece(first)) {
                continue;
            }

            if (!board.hasPiece(to) || !board.isMyTeam(country, to)) {
                availables.add(to);
            }
        }

        return availables;
    }
}
