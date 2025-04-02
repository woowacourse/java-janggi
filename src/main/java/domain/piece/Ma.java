package domain.piece;

import domain.piece.coordiante.Coordinate;
import domain.board.ReadableBoard;
import domain.piece.movement.Movement;
import domain.piece.movement.Movements;
import java.util.ArrayList;
import java.util.List;

public class Ma extends Piece {

    private static final List<Movements> movements = List.of(
            new Movements(List.of(Movement.UP, Movement.UP_UP_LEFT)),
            new Movements(List.of(Movement.UP, Movement.UP_UP_RIGHT)),
            new Movements(List.of(Movement.DOWN, Movement.DOWN_DOWN_LEFT)),
            new Movements(List.of(Movement.DOWN, Movement.DOWN_DOWN_RIGHT)),
            new Movements(List.of(Movement.LEFT, Movement.UP_LEFT_LEFT)),
            new Movements(List.of(Movement.LEFT, Movement.DOWN_LEFT_LEFT)),
            new Movements(List.of(Movement.RIGHT, Movement.UP_RIGHT_RIGHT)),
            new Movements(List.of(Movement.RIGHT, Movement.DOWN_RIGHT_RIGHT))
    );

    public Ma(Country country) {
        super(country, PieceType.MA);
    }

    @Override
    public List<Coordinate> findAvailablePaths(Coordinate from, ReadableBoard readableBoard) {
        List<Coordinate> availables = new ArrayList<>();

        for (Movements movements : movements) {
            Coordinate first = from.move(movements.getMovements().get(0));
            Coordinate to = from.move(movements.getMovements().get(1));

            if (first.isOutOfBoundary() || to.isOutOfBoundary()) {
                continue;
            }
            if (readableBoard.hasPiece(first)) {
                continue;
            }

            if (!readableBoard.hasPiece(to) || !readableBoard.isMyTeam(country, to)) {
                availables.add(to);
            }
        }

        return availables;
    }
}
