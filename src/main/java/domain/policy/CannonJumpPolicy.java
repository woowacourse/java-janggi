package domain.policy;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class CannonJumpPolicy implements MovePolicy {

    private static final int JUMP_THRESHOLD = 1;

    @Override
    public List<Position> apply(Board board, Position start, List<Direction> directions) {
        List<Position> possibleMoves = new ArrayList<>();
        Position current = start;
        int jumpCount = 0;

        for (Direction direction : directions) {
            current = current.nextPosition(direction);
            Piece target = board.getPieceBy(current);

            if (target.getType() == PieceType.CANNON) {
                break;
            }

            if (jumpCount == JUMP_THRESHOLD) {
                possibleMoves.add(current);
            }

            if (!board.isEmpty(current)) {
                jumpCount++;
            }
        }

        return List.copyOf(possibleMoves);
    }
}
