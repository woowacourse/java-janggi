package domain.piece;

import domain.board.Palace;
import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.error.PieceException;
import java.util.List;

public class Soldier extends Piece {

    private static final Palace PALACE = new Palace();
    private static final MoveDelta ONE_STEP_DIAGONAL = new MoveDelta(1, 1);
    private static final List<MoveDelta> CHO_MOVABLE_LOCATION = List.of(
            new MoveDelta(-1, 0),
            new MoveDelta(0, -1),
            new MoveDelta(1, 0)
    );
    private static final List<MoveDelta> HAN_MOVABLE_LOCATION = List.of(
            new MoveDelta(-1, 0),
            new MoveDelta(0, 1),
            new MoveDelta(1, 0)
    );

    public Soldier(Team team) {
        super(team);
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        validateLocation(from, to);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.SOLDIER;
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

        if (movableLocation().contains(different)) {
            return;
        }
        if (!canMoveForwardDiagonallyInEnemyPalace(from, to)) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    private List<MoveDelta> movableLocation() {
        if (team.isCho()) {
            return CHO_MOVABLE_LOCATION;
        }
        return HAN_MOVABLE_LOCATION;
    }

    private boolean canMoveForwardDiagonallyInEnemyPalace(Coordination from, Coordination to) {
        return isInEnemyPalace(from)
                && isInEnemyPalace(to)
                && isDiagonalOneStepInPalace(from, to)
                && isForward(MoveDelta.between(from, to));
    }

    private boolean isInEnemyPalace(Coordination coordination) {
        if (team.isCho()) {
            return PALACE.isTopPalace(coordination);
        }
        return PALACE.isBottomPalace(coordination);
    }

    private boolean isDiagonalOneStepInPalace(Coordination from, Coordination to) {
        MoveDelta absolute = MoveDelta.between(from, to).absolute();
        return ONE_STEP_DIAGONAL.equals(absolute) && PALACE.hasDiagonalRoute(from, to);
    }

    private boolean isForward(MoveDelta delta) {
        if (team.isCho()) {
            return delta.deltaRow() == -1;
        }
        return delta.deltaRow() == 1;
    }
}
