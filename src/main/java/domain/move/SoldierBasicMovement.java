package domain.move;

import domain.coordination.Coordination;
import domain.coordination.MoveDelta;
import domain.piece.Team;
import java.util.List;

public class SoldierBasicMovement implements Movement {

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

    @Override
    public boolean canMove(Coordination from, Coordination to, Team team) {
        return movableLocation(team).contains(MoveDelta.between(from, to));
    }

    private List<MoveDelta> movableLocation(Team team) {
        if (team.isCho()) {
            return CHO_MOVABLE_LOCATION;
        }
        return HAN_MOVABLE_LOCATION;
    }

    @Override
    public List<Coordination> path(Coordination from, Coordination to) {
        return List.of();
    }
}
