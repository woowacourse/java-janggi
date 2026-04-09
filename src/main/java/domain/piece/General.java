package domain.piece;

import domain.board.MoveContext;
import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.error.PieceException;
import java.util.List;

public class General extends Piece {
    private static final MoveDelta ONE_STEP_DIAGONAL = new MoveDelta(1, 1);

    public General(Team team) {
        super(team);
    }

    @Override
    public void validateRule(MoveContext moveContext) {
        if (!canMoveOneStepInPalace(moveContext)) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    @Override
    public PieceType pieceType() {
        return PieceType.GENERAL;
    }

    @Override
    public boolean isAliveGeneral() {
        return true;
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

    private boolean canMoveOneStepInPalace(MoveContext moveContext) {
        Coordination from = moveContext.from();
        Coordination to = moveContext.to();
        return moveContext.isSamePalace()
                && (isOrthogonalOneStep(from, to) || isDiagonalOneStepInPalace(moveContext, from, to));
    }

    private boolean isOrthogonalOneStep(Coordination from, Coordination to) {
        MoveDelta absolute = MoveDelta.between(from, to).absolute();
        return absolute.deltaColumn() + absolute.deltaRow() == 1;
    }

    private boolean isDiagonalOneStepInPalace(MoveContext moveContext, Coordination from, Coordination to) {
        MoveDelta absolute = MoveDelta.between(from, to).absolute();
        return ONE_STEP_DIAGONAL.equals(absolute) && moveContext.isPalaceDiagonalMove();
    }
}
