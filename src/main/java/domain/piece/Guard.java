package domain.piece;

import domain.board.Palace;
import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.error.PieceException;
import java.util.List;

public class Guard extends Piece {

    private static final Palace PALACE = new Palace();
    private static final MoveDelta ONE_STEP_DIAGONAL = new MoveDelta(1, 1);

    public Guard(Team team) {
        super(team);
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        if (!canMoveOneStepInPalace(from, to)) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    @Override
    public PieceType pieceType() {
        return PieceType.GUARD;
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

    private boolean canMoveOneStepInPalace(Coordination from, Coordination to) {
        return PALACE.isSamePalace(from, to)
                && (isOrthogonalOneStep(from, to) || isDiagonalOneStepInPalace(from, to));
    }

    private boolean isOrthogonalOneStep(Coordination from, Coordination to) {
        MoveDelta absolute = MoveDelta.between(from, to).absolute();
        return absolute.deltaColumn() + absolute.deltaRow() == 1;
    }

    private boolean isDiagonalOneStepInPalace(Coordination from, Coordination to) {
        MoveDelta absolute = MoveDelta.between(from, to).absolute();
        return ONE_STEP_DIAGONAL.equals(absolute) && PALACE.hasDiagonalRoute(from, to);
    }
}
