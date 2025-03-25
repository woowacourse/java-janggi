package domain.piece;

import domain.Team;
import java.util.function.Function;

public final class PieceFactory {

    private PieceFactory() {
    }

    public static <T extends Piece> T createGreenTeam(final Function<Team, T> creator) {
        return creator.apply(Team.GREEN);
    }

    public static <T extends Piece> T createRedTeam(final Function<Team, T> creator) {
        return creator.apply(Team.RED);
    }
}
