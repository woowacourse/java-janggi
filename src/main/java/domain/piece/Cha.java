package domain.piece;

import static domain.piece.PieceType.CHA;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.ArrayList;
import java.util.List;

public class Cha extends Piece {

    private static final List<Movement> MOVEMENTS = List.of(
            Movement.UP, Movement.DOWN, Movement.RIGHT, Movement.LEFT
    );

    public Cha(Country country) {
        super(country, CHA);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate from, Board board) {
        List<Coordinate> availablePositions = new ArrayList<>();

        for (Movement movement : MOVEMENTS) {
            Coordinate next = from.move(movement);

            while (!next.isOutOfBoundary()) {
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

        return availablePositions;
    }

}
