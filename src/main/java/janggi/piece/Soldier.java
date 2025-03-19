package janggi.piece;

import java.util.ArrayList;
import java.util.List;

public class Soldier extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(7, 1),
            new Position(7, 3),
            new Position(7, 5),
            new Position(7, 7),
            new Position(7, 9));
    private static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(4, 1),
            new Position(4, 3),
            new Position(4, 5),
            new Position(4, 7),
            new Position(4, 9));

    public Soldier(final Position position, final TeamType teamType) {
        super(position, teamType);
    }

    public static List<Soldier> createWithInitialPositions(TeamType teamType) {
        List<Soldier> soldiers = new ArrayList<>();
        if(teamType.equals(TeamType.BLUE)) {
            INITIAL_POSITIONS_BLUE.forEach(position -> {
                soldiers.add(new Soldier(position, teamType));
            });
            return soldiers;
        }
        INITIAL_POSITIONS_RED.forEach(position -> {
            soldiers.add(new Soldier(position, teamType));
        });
        return soldiers;
    }
}
