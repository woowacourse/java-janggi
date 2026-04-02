package domain.piece;

import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.error.PieceException;
import java.util.List;

public class Guard extends Piece {

    private static final List<MoveDelta> MOVABLE_LOCATION = List.of(
            new MoveDelta(-1, 0),
            new MoveDelta(0, -1),
            new MoveDelta(1, 0),
            new MoveDelta(0, 1)
    );

    public Guard(Team team) {
        super(team);
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        validateLocation(from, to);
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        return List.of();
    }

    @Override
    public void validatePath(List<Piece> piecesOnPath) {
        if (!piecesOnPath.isEmpty()) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    private void validateLocation(Coordination from, Coordination to) {
        MoveDelta different = MoveDelta.between(from, to);

        validateLocation(different);
    }

    private void validateLocation(MoveDelta different) {
        boolean isMovable = MOVABLE_LOCATION.contains(different);

        if (!isMovable) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }
}
