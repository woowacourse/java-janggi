package domain.piece;

import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.coordination.MoveDeltas;
import domain.piece.error.ErrorMessage;
import domain.piece.error.PieceException;
import java.util.Map;
import java.util.Set;

public class Guard extends Piece {

    private static final MoveDeltas MOVABLE_LOCATION = MoveDeltas.of(Set.of(
            new MoveDelta(-1, 0),
            new MoveDelta(0, -1),
            new MoveDelta(1, 0),
            new MoveDelta(0, 1)
    ));

    public Guard(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {
        validateLocation(from, to);
        validateSameTeam(from, to, board);
    }

    private void validateLocation(Coordination from, Coordination to) {
        MoveDelta different = MoveDelta.between(from, to);

        validateLocation(different);
    }

    private void validateLocation(MoveDelta different) {
        boolean isMovable = MOVABLE_LOCATION.contains(different);

        if (!isMovable) {
            throw new PieceException(ErrorMessage.IMPOSSIBLE_MOVE.getMessage());
        }
    }
}
