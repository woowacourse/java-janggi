package domain.piece;

import domain.board.MoveContext;
import domain.board.PalaceArea;
import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.error.PieceException;
import java.util.List;

public class Soldier extends Piece {
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
    public void validateRule(MoveContext moveContext) {
        validateLocation(moveContext);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.SOLDIER;
    }

    @Override
    public List<Coordination> resolvePath(MoveContext moveContext) {
        return List.of();
    }

    @Override
    public void validatePath(List<Piece> piecesOnPath) {
        if (!piecesOnPath.isEmpty()) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    private void validateLocation(MoveContext moveContext) {
        Coordination from = moveContext.from();
        Coordination to = moveContext.to();
        MoveDelta different = MoveDelta.between(from, to);

        if (movableLocation().contains(different)) {
            return;
        }
        if (!canMoveForwardDiagonallyInEnemyPalace(moveContext, from, to)) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    private List<MoveDelta> movableLocation() {
        if (team.isCho()) {
            return CHO_MOVABLE_LOCATION;
        }
        return HAN_MOVABLE_LOCATION;
    }

    private boolean canMoveForwardDiagonallyInEnemyPalace(MoveContext moveContext, Coordination from, Coordination to) {
        return isFromInEnemyPalace(moveContext)
                && isToInEnemyPalace(moveContext)
                && isDiagonalOneStepInPalace(moveContext, from, to)
                && isForward(MoveDelta.between(from, to));
    }

    private boolean isFromInEnemyPalace(MoveContext moveContext) {
        if (team.isCho()) {
            return moveContext.fromPalaceArea() == PalaceArea.TOP;
        }
        return moveContext.fromPalaceArea() == PalaceArea.BOTTOM;
    }

    private boolean isToInEnemyPalace(MoveContext moveContext) {
        if (team.isCho()) {
            return moveContext.toPalaceArea() == PalaceArea.TOP;
        }
        return moveContext.toPalaceArea() == PalaceArea.BOTTOM;
    }

    private boolean isDiagonalOneStepInPalace(MoveContext moveContext, Coordination from, Coordination to) {
        MoveDelta absolute = MoveDelta.between(from, to).absolute();
        return ONE_STEP_DIAGONAL.equals(absolute) && moveContext.isPalaceDiagonalMove();
    }

    private boolean isForward(MoveDelta delta) {
        if (team.isCho()) {
            return delta.deltaRow() == -1;
        }
        return delta.deltaRow() == 1;
    }
}
