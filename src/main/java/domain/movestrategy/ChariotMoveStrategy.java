package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.Position;
import java.util.ArrayList;
import java.util.List;

public class ChariotMoveStrategy implements MoveStrategy {

    private static final List<Delta> ORTHOGONAL = List.of(
            Delta.UP,
            Delta.RIGHT,
            Delta.DOWN,
            Delta.LEFT
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Board board) {
        final List<Position> movable = new ArrayList<>();

        for (final Delta delta : ORTHOGONAL) {
            movable.addAll(calculateByDirection(from, board, delta));
        }

        return movable;
    }


    private List<Position> calculateByDirection(
            final Position from,
            final Board board,
            final Delta delta
    ) {
        final List<Position> movable = new ArrayList<>();
        final Piece fromPiece = board.getPiece(from);

        for (Position current = from.move(delta); board.inBoard(current); current = current.move(delta)) {
            if (!board.hasPiece(current)) {
                movable.add(current);
                continue;
            }

            if (!fromPiece.isSameTeam(board.getPiece(current))) {
                movable.add(current);
            }

            break;
        }

        return movable;
    }
}
