package domain;

import java.util.List;
import java.util.Map;

public class General extends Piece {

    public static final Map<Team, List<Position>> INITIAL_POSITION = Map.of(
        Team.GREEN, List.of(new Position(4, 1)),
        Team.RED, List.of(new Position(4, 8))
    );

    public General(final Team team) {
        super(team);
    }
}
