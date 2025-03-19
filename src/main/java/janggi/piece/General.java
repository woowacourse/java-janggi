package janggi.piece;

import java.util.List;

public class General extends Piece {
    private static final Position INITIAL_POSITIONS_BLUE = new Position(9, 5);
    private static final Position INITIAL_POSITIONS_RED = new Position(2, 5);

    public General(final Position position, final TeamType teamType) {
        super(position, teamType);
    }

    public static List<Piece> createWithInitialPositions(TeamType teamType) {
        if(teamType.equals(TeamType.BLUE)) {
            return List.of(new General(INITIAL_POSITIONS_BLUE, teamType));
        }
        return List.of(new General(INITIAL_POSITIONS_RED, teamType));
    }
}
