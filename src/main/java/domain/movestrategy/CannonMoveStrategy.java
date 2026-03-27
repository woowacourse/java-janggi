package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CannonMoveStrategy implements MoveStrategy {

    private static final List<Delta> ORTHOGONAL_DELTAS = List.of(
            Delta.UP,
            Delta.RIGHT,
            Delta.DOWN,
            Delta.LEFT
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from,
                                                    final Map<Position, Piece> pieces) {
        List<Position> movable = new ArrayList<>();

        for (final Delta delta : ORTHOGONAL_DELTAS) {
            movable.addAll(calculateMovableByDirection(from, pieces, delta));
        }

        return movable;
    }

    private List<Position> calculateMovableByDirection(
            final Position from,
            final Map<Position, Piece> pieces,
            final Delta delta
    ) {
        List<Position> movable = new ArrayList<>();

        boolean metPiece = false;

        for (Position current = from.move(delta);
             inBoard(current);
             current = current.move(delta)) {

            // 빈 칸
            if (!pieces.containsKey(current)) {
                if (metPiece) {
                    movable.add(current);
                }
                continue;
            }

            Piece piece = pieces.get(current);

            // 두 번째 기물
            if (metPiece) {
                if (!piece.isCannon()) {
                    movable.add(current);
                }
                break;
            }

            // 첫 번째 기물
            if (piece.isCannon()) {
                break;
            }

            metPiece = true;
        }

        return movable;
    }

    private boolean inBoard(final Position current) {
        return (current.column() >= Board.MIN_COLUMN_RANGE && current.column() <= Board.MAX_COLUMN_RANGE)
                && (current.row() >= Board.MIN_ROW_RANGE && current.row() <= Board.MAX_ROW_RANGE);
    }
}
