package domain.piece;

import static domain.piece.PieceType.CHA;

import domain.piece.coordiante.Coordinate;
import domain.board.ReadableBoard;
import domain.piece.movement.Movement;
import domain.piece.movement.Movements;
import java.util.ArrayList;
import java.util.List;

public class Cha extends Piece {

    private final Movements movements = new Movements(
            List.of(Movement.UP, Movement.RIGHT, Movement.LEFT, Movement.DOWN));

    public Cha(Country country) {
        super(country, CHA);
    }

    @Override
    public List<Coordinate> findAvailablePaths(Coordinate from, ReadableBoard readableBoard) {
        movements.addMovementIfInGung(from);

        List<Coordinate> availablePositions = new ArrayList<>();

        for (Movement movement : movements.getMovements()) {
            Coordinate next = from.move(movement);

            while (movement.isDiagonal() ? next.isInGungBoundary() : next.isInBoundary()) {
                if (readableBoard.hasPiece(next)) {
                    if (!readableBoard.isMyTeam(country, next)) {
                        availablePositions.add(next);
                    }
                    break;
                }
                availablePositions.add(next);
                next = next.move(movement);
            }
        }

        return availablePositions;
    }

}
