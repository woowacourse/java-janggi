package domain;

import java.util.function.Function;

public class PieceFactory {
    public static <T extends Piece> T createGreenTeam(Function<Team, T> creator) {
        return creator.apply(Team.GREEN);
    }

    public static <T extends Piece> T createRedTeam(Function<Team, T> creator) {
        return creator.apply(Team.RED);
    }
}
