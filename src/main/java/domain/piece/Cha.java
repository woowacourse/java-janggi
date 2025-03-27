package domain.piece;

import static domain.piece.PieceType.CHA;

import domain.Coordinate;
import domain.board.Board;
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
    public List<Coordinate> findAvailablePaths(Coordinate from, Board board) {
        movements.addMovementIfInGung(from);

        List<Coordinate> availablePositions = new ArrayList<>();
        for (Movement movement : movements.getMovements()) {
            Coordinate next = from.move(movement);

            if (movement.isDiagonal()) {
                while (next.isInGungBoundary()) {
                    if (board.hasPiece(next)) {
                        if (!board.isMyTeam(country, next)) {
                            availablePositions.add(next);
                        }
                        break;
                    }
                    availablePositions.add(next);
                    next = next.move(movement);
                }
            } else {
                while (next.isInBoundary()) {
                    if (board.hasPiece(next)) {
                        if (!board.isMyTeam(country, next)) {
                            availablePositions.add(next);
                        }
                        break;
                    }
                    availablePositions.add(next);
                    next = next.move(movement);
                }
            }
        }
        return availablePositions;
    }

}
