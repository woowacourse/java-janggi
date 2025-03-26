package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.ArrayList;
import java.util.List;

public class Pho extends Piece {

    private static final List<Movement> MOVEMENTS = List.of(
            Movement.UP, Movement.DOWN, Movement.RIGHT, Movement.LEFT
    );

    public Pho(Country country) {
        super(country, PieceType.PHO);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate from, Board board) {
        List<Coordinate> availablePositions = new ArrayList<>();

        for (Movement movement : MOVEMENTS) {
            Coordinate next = from.move(movement);
            boolean isJumped = false;

            while (!next.isOutOfBoundary()) {

                if (!isJumped) {
                    if (board.hasPiece(next)) {
                        if (board.findPieceTypeByCoordinate(next) == PieceType.PHO) {
                            break;
                        }
                        isJumped = true;
                    }
                } else {
                    if (!board.hasPiece(next)) {
                        availablePositions.add(next);
                    } else {
                        canCapture(board, next, availablePositions);
                        break;
                    }
                }

                next = next.move(movement);
            }
        }

        return availablePositions;
    }

    private void canCapture(Board board, Coordinate next, List<Coordinate> availablePositions) {
        if (!board.isMyTeam(country, next) && board.findPieceTypeByCoordinate(next) != PieceType.PHO) {
            availablePositions.add(next);
        }
    }

}
