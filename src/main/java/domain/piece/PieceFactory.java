package domain.piece;

import domain.Score;
import domain.Team;
import java.util.function.BiFunction;

public final class PieceFactory {

    private PieceFactory() {
    }

    public static <T extends Piece> T createGreenTeam(BiFunction<Team, Score, T> creator, Score score) {
        return creator.apply(Team.GREEN, score);
    }

    public static <T extends Piece> T createRedTeam(BiFunction<Team, Score, T> creator, Score score) {
        return creator.apply(Team.RED, score);
    }

    public static Cannon createCannon() {
        return new Cannon(Team.RED, Score.CANNON);
    }

    public static General createGeneral() {
        return new General(Team.RED, Score.GENERAL);
    }
}
