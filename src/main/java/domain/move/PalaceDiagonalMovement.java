package domain.move;

import domain.board.palace.Palace;
import domain.coordination.Coordination;
import domain.piece.Team;
import java.util.List;

public class PalaceDiagonalMovement implements Movement {

    private static final Palace PALACE = new Palace();

    @Override
    public boolean canMove(Coordination from, Coordination to, Team team) {
        return PALACE.diagonalRoute(from, to).exists();
    }

    @Override
    public List<Coordination> path(Coordination from, Coordination to) {
        return PALACE.diagonalRoute(from, to).path();
    }
}
