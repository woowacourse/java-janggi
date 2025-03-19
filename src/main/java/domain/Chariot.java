package domain;

import java.util.List;
import java.util.Map;

public class Chariot extends Piece {

    public static final Map<Team, List<Position>> INITIAL_POSITION = Map.of(
        Team.GREEN, List.of(new Position(0, 0), new Position(8, 0)),
        Team.RED, List.of(new Position(0, 9), new Position(8, 9))
    );

    public Chariot(final Team team) {
        super(team);
    }
}
