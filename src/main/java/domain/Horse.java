package domain;

import java.util.List;
import java.util.Map;

public class Horse extends Piece {

    public static final Map<Team, List<Position>> INITIAL_POSITION = Map.of(
        Team.GREEN, List.of(new Position(2, 0), new Position(6, 0)),
        Team.RED, List.of(new Position(2, 9), new Position(6, 9))
    );

    public Horse(final Team team) {
        super(team);
    }
}
