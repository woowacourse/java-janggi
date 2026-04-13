package domain.move;

import domain.board.palace.Palace;
import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.Team;
import java.util.List;

public class PalaceOneStepMovement implements Movement {

    private static final Palace PALACE = new Palace();

    @Override
    public boolean canMove(Coordination from, Coordination to, Team team) {
        MoveDelta delta = MoveDelta.between(from, to);
        return PALACE.isSamePalace(from, to)
                && (delta.isOrthogonalOneStep() || isPalaceDiagonalOneStep(from, to, delta));
    }

    private boolean isPalaceDiagonalOneStep(Coordination from, Coordination to, MoveDelta delta) {
        return delta.isDiagonalOneStep()
                && PALACE.diagonalRoute(from, to).exists();
    }

    @Override
    public List<Coordination> path(Coordination from, Coordination to) {
        return List.of();
    }
}
