package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChariotMoveStrategy implements MoveStrategy {

    private static final List<Delta> ORTHOGONAL = List.of(
            Delta.UP,
            Delta.RIGHT,
            Delta.DOWN,
            Delta.LEFT
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        List<Position> movable = new ArrayList<>();

        for (final Delta delta : ORTHOGONAL) {
            movable.addAll(calculateByDirection(from, pieces, delta));
        }

        return movable;
    }


    private List<Position> calculateByDirection(
            final Position from,
            final Map<Position, Piece> pieces,
            final Delta delta
    ) {
        List<Position> movable = new ArrayList<>();
        Piece fromPiece = pieces.get(from);

        for (Position current = from.move(delta); inBoard(current); current = current.move(delta)) {
            if (!pieces.containsKey(current)) {
                movable.add(current);
                continue;
            }

            Piece target = pieces.get(current);
            if (fromPiece.getTeam() != target.getTeam()) {
                movable.add(current);
            }

            break;
        }

        return movable;
    }

    private boolean inBoard(final Position current) {
        return current.column() >= Board.MIN_COLUMN_RANGE && current.column() <= Board.MAX_COLUMN_RANGE
                && current.row() >= Board.MIN_ROW_RANGE && current.row() <= Board.MAX_ROW_RANGE;
    }
}
