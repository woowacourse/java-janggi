package domain.movestrategy;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Delta;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class ChariotMoveStrategy extends BasicMoveStrategy {

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {
        Piece chariot = board.getPieceAt(from);
        List<Position> movable = new ArrayList<>();

        for (Delta direction : Delta.ORTHOGONAL_DELTAS) {
            collectMovablePositions(board, chariot, from, direction, movable);
        }

        return movable;
    }

    private void collectMovablePositions(
            final Board board,
            final Piece chariot,
            final Position from,
            final Delta direction,
            final List<Position> movable
    ) {
        Position current = from;

        while (current.canMove(direction)) {
            current = current.move(direction);

            if (board.isEmpty(current)) {
                movable.add(current);
                continue;
            }

            Piece target = board.getPieceAt(current);
            if (canCapture(chariot, target)) {
                movable.add(current);
            }
            return;
        }
    }

    private boolean canCapture(Piece chariot, Piece target) {
        return chariot.isOpposite(target);
    }
}
