package domain.piece;

import static domain.piece.PieceType.CHA;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.ArrayList;
import java.util.List;

public class Cha extends Piece {

    private final List<Movement> MOVEMENTS = List.of(
            Movement.UP, Movement.DOWN, Movement.RIGHT, Movement.LEFT);

    public Cha(Country country) {
        super(country, CHA);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate currCoordinate, Board board) {
        List<Coordinate> availablePositions = new ArrayList<>();

        for (Movement movement : MOVEMENTS) {
            Coordinate next = currCoordinate.move(movement);

            while (!next.isOutOfBoundary()) {
                if (board.hasPiece(next)) {
                    if (!board.isMyTeam(currCoordinate, next)) {
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
