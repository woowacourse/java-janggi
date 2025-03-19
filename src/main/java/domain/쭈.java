package domain;

import java.util.List;
import java.util.Map;

public class 쭈 extends Piece {

    public static final Map<Team, List<Position>> INITIAL_POSITION = Map.of(
        Team.GREEN, List.of(new Position(0, 3), new Position(2, 3),new Position(4, 3), new Position(6, 3),new Position(8, 3)),
        Team.RED, List.of(new Position(0, 6), new Position(2, 6),new Position(4, 6), new Position(6, 6),new Position(8, 6))
    );

    public 쭈(final Team team) {
        super(team);
    }
}
