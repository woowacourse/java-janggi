package domain.movestrategy;

import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChariotMoveStrategy extends BasicMoveStrategy {

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
        Piece currentPiece = pieces.get(from);

        Position nextPosition = from.move(delta);

        while (isInsideBoard(nextPosition)) {
            Piece targetPiece = pieces.get(nextPosition);

            if (targetPiece == null) {
                movable.add(nextPosition);
                nextPosition = nextPosition.move(delta);
                continue;
            }

            if (!currentPiece.isSameTeam(targetPiece)) {
                movable.add(nextPosition);
            }
            break;
        }

        return movable;
    }
}
