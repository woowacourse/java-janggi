package domain.piece;

import domain.move.MoveContext;
import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.error.PieceException;
import java.util.List;

public class Soldier extends Piece {
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
        if (!canMoveForwardDiagonallyInEnemyPalace(moveContext, from)) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    private List<MoveDelta> movableLocation() {
        if (team.isCho()) {
            return CHO_MOVABLE_LOCATION;
        }
        return HAN_MOVABLE_LOCATION;
    }

    private boolean canMoveForwardDiagonallyInEnemyPalace(MoveContext moveContext, Coordination from) {
        return moveContext.isFromInEnemyPalace(team)
                && moveContext.isToInEnemyPalace(team)
                && moveContext.isPalaceDiagonalOneStep()
                && isForward(MoveDelta.between(from, moveContext.to()));
    }

    private boolean isForward(MoveDelta delta) {
        if (team.isCho()) {
            return delta.deltaRow() == -1;
        }
        return delta.deltaRow() == 1;
    }
}
