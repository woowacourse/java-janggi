package domain.policy;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class CannonCapturePolicy implements MovePolicy {

    @Override
    public List<Position> apply(Board board, Position start, List<Direction> directions) {
        List<Position> possibleMoves = new ArrayList<>();
        Position current = start;

        for (Direction direction : directions) {
            current = current.nextPosition(direction);
            Piece targetPiece = board.getPieceBy(current);

            if (!(targetPiece.getType() == PieceType.CANNON)) {
                possibleMoves.add(current);
            }
        }

        return List.copyOf(possibleMoves);
    }
}
