package domain.movestrategy;

import domain.board.Position;
import domain.piece.Delta;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CannonMoveStrategy extends BasicMoveStrategy {

    @Override
    public List<Position> calculateMovablePositions(final Position from,
                                                    final Map<Position, Piece> pieces) {
        List<Position> movable = new ArrayList<>();

        for (final Delta delta : Delta.ORTHOGONAL_DELTAS) {
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
             isInsideBoard(current);
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
                if (!piece.isCannon() && isEmptyOrOpposite(from, current, pieces)) {
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
}
