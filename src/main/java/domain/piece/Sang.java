package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import domain.piece.movement.Movements;
import java.util.ArrayList;
import java.util.List;

public class Sang extends Piece {

    private static final List<Movements> movements = List.of(
            new Movements(List.of(Movement.UP, Movement.UP_UP_RIGHT, Movement.UP_UP_UP_RIGHT_RIGHT)),
            new Movements(List.of(Movement.UP, Movement.UP_UP_LEFT, Movement.UP_UP_UP_LEFT_LEFT)),
            new Movements(List.of(Movement.DOWN, Movement.DOWN_DOWN_RIGHT, Movement.DOWN_DOWN_DOWN_RIGHT_RIGHT)),
            new Movements(List.of(Movement.DOWN, Movement.DOWN_DOWN_LEFT, Movement.DOWN_DOWN_DOWN_LEFT_LEFT)),
            new Movements(List.of(Movement.RIGHT, Movement.UP_RIGHT_RIGHT, Movement.UP_UP_RIGHT_RIGHT_RIGHT)),
            new Movements(List.of(Movement.RIGHT, Movement.DOWN_RIGHT_RIGHT, Movement.DOWN_DOWN_RIGHT_RIGHT_RIGHT)),
            new Movements(List.of(Movement.LEFT, Movement.UP_LEFT_LEFT, Movement.UP_UP_LEFT_LEFT_LEFT)),
            new Movements(List.of(Movement.LEFT, Movement.DOWN_LEFT_LEFT, Movement.DOWN_DOWN_LEFT_LEFT_LEFT))
    );


    public Sang(Country country) {
        super(country, PieceType.SANG);
    }

    @Override
    public List<Coordinate> findAvailablePaths(Coordinate from, Board board) {
        List<Coordinate> availables = new ArrayList<>();

        for (Movements movements : movements) {
            Coordinate first = from.move(movements.getMovements().get(0));
            Coordinate second = from.move(movements.getMovements().get(1));
            Coordinate to = from.move(movements.getMovements().get(2));

            if (first.isOutOfBoundary() || second.isOutOfBoundary() || to.isOutOfBoundary()) {
                continue;
            }
            if (board.hasPiece(first) || board.hasPiece(second)) {
                continue;
            }
            if (!board.hasPiece(to) || !board.isMyTeam(country, to)) {
                availables.add(to);
            }
        }

        return availables;
    }
}
