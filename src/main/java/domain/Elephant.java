package domain;

import java.util.List;
import java.util.Map;

public class Elephant extends Piece {

    public static final Map<Team, List<Position>> INITIAL_POSITION = Map.of(
        Team.GREEN, List.of(new Position(1, 0), new Position(7, 0)),
        Team.RED, List.of(new Position(1, 9), new Position(7, 9))
    );

    public Elephant(final Team team) {
        super(team);
    }
}
