package janggi.piece;

import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {
    private static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(8, 2),
            new Position(8, 8));
    private static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(3, 2),
            new Position(3, 8));

    private Cannon(final Position position, final TeamType teamType) {
        super(position, teamType);
    }

    public static List<Cannon> createWithInitialPositions(TeamType teamType) {
        List<Cannon> cannons = new ArrayList<>();
        if (teamType.equals(TeamType.BLUE)) {
            INITIAL_POSITIONS_BLUE.forEach(position -> {
                cannons.add(new Cannon(position, teamType));
            });
            return cannons;
        }
        INITIAL_POSITIONS_RED.forEach(position -> {
            cannons.add(new Cannon(position, teamType));
        });
        return cannons;
    }
}