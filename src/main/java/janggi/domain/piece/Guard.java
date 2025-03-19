package janggi.domain.piece;

import java.util.ArrayList;
import java.util.List;

public class Guard extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE = List.of(new Position(10, 4), new Position(10, 6));
    private static final List<Position> INITIAL_POSITIONS_RED = List.of(new Position(1, 4), new Position(1, 6));

    public Guard(final Position position, final TeamType teamType) {
        super("사", position, teamType);
    }

    public static List<Piece> createWithInitialPositions(final TeamType teamType) {
        List<Piece> guards = new ArrayList<>();
        if (teamType.equals(TeamType.BLUE)) {
            INITIAL_POSITIONS_BLUE.forEach(position ->
                    guards.add(new Guard(position, teamType)));
            return guards;
        }
        INITIAL_POSITIONS_RED.forEach(position ->
                guards.add(new Guard(position, teamType)));
        return guards;
    }
}
