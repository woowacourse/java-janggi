package domain.piece;

import domain.Score;
import domain.Team;
import java.util.function.BiFunction;

public final class PieceFactory {

    private PieceFactory() {
    }

    public static <T extends Piece> T createGreenTeam(final BiFunction<Team, Score, T> creator, final Score score) {
        return creator.apply(Team.GREEN, score);
    }

    public static <T extends Piece> T createRedTeam(final BiFunction<Team, Score, T> creator, final Score score) {
        return creator.apply(Team.RED, score);
    }

    public static Cannon createCannon() {
        return new Cannon(Team.RED, Score.CANNON);
    }

    public static General createGeneral() {
        return new General(Team.RED, Score.GENERAL);
    }
}
