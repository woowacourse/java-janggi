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
        List<Coordinate> availables = new ArrayList<>();

        for (Movement movement : MOVEMENTS) {
            Coordinate next = from.move(movement);

            while (!next.isOutOfBoundary()) {
                if (board.hasPiece(next) && !board.isMyTeam(country, next)) {
                    availables.add(next);
                    break;
                }

                availables.add(next);
                next = next.move(movement);
            }
        }

        return availables;
    }
}
