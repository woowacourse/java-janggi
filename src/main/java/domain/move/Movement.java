package domain.move;

import domain.coordination.Coordination;
import domain.piece.Team;
import java.util.List;

public interface Movement {

    boolean canMove(Coordination from, Coordination to, Team team);

    List<Coordination> path(Coordination from, Coordination to);
}
