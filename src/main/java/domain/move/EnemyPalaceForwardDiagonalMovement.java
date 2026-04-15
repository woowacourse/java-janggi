package domain.move;

import domain.board.palace.Palace;
import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.Team;
import java.util.List;

public class EnemyPalaceForwardDiagonalMovement implements Movement {

    private static final Palace PALACE = new Palace();

    @Override
    public boolean canMove(Coordination from, Coordination to, Team team) {
        MoveDelta delta = MoveDelta.between(from, to);
        return PALACE.isEnemyPalace(from, team)
                && PALACE.isEnemyPalace(to, team)
                && delta.isDiagonalOneStep()
                && PALACE.diagonalRoute(from, to).exists()
                && isForward(delta, team);
    }

    private boolean isForward(MoveDelta delta, Team team) {
        if (team.isCho()) {
            return delta.deltaRow() == -1;
        }
        return delta.deltaRow() == 1;
    }

    @Override
    public List<Coordination> path(Coordination from, Coordination to) {
        return List.of();
    }
}
