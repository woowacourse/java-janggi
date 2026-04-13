package domain.move;

import domain.coordination.Coordination;
import domain.piece.Team;
import java.util.List;

public class StraightMovement implements Movement {

    @Override
    public boolean canMove(Coordination from, Coordination to, Team team) {
        return from.isSameRowDifferentColumn(to)
                || from.isSameColumnDifferentRow(to);
    }

    @Override
    public List<Coordination> path(Coordination from, Coordination to) {
        if (from.isSameColumnDifferentRow(to)) {
            return from.betweenRowCoordination(to);
        }
        return from.betweenColumnCoordination(to);
    }
}
