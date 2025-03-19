package domain;

import java.util.List;
import java.util.Map;

public class Cannon extends Piece {

    public static final Map<Team, List<Position>> INITIAL_POSITION = Map.of(
        Team.GREEN, List.of(new Position(1, 2), new Position(7, 2)),
        Team.RED, List.of(new Position(1, 7), new Position(7, 7))
    );

    public Cannon(final Team team) {
        super(team);
    }
}
