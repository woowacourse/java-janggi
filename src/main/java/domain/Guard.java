package domain;

import java.util.List;
import java.util.Map;

public class Guard extends Piece {

    public static final Map<Team, List<Position>> INITIAL_POSITION = Map.of(
        Team.GREEN, List.of(new Position(3, 0), new Position(5, 0)),
        Team.RED, List.of(new Position(3, 9), new Position(5, 9))
    );

    public Guard(final Team team) {
        super(team);
    }
}
