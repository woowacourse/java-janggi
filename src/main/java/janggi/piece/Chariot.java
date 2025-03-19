package janggi.piece;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(10, 1),
            new Position(10, 9));
    private static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(1, 1),
            new Position(1, 9));

    private Chariot(final Position position, final TeamType teamType) {
        super(position, teamType);
    }

    public static List<Piece> createWithInitialPositions(TeamType teamType) {
        List<Piece> chariots = new ArrayList<>();
        if(teamType.equals(TeamType.BLUE)) {
            INITIAL_POSITIONS_BLUE.forEach(position -> {
                chariots.add(new Chariot(position, teamType));
            });
            return chariots;
        }
        INITIAL_POSITIONS_RED.forEach(position -> {
            chariots.add(new Chariot(position, teamType));
        });
        return chariots;
    }
}
