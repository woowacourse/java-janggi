package janggi.domain.status;

import java.util.Map;
import java.util.function.Supplier;

public class GameStatusFactory {

    private static final Map<Team, Supplier<GameStatus>> FACTORY = Map.of(
            Team.CHO, ChoTurn::new,
            Team.HAN, HanTurn::new
    );

    public static GameStatus create(Team team) {
        Supplier<GameStatus> supplier = FACTORY.get(team);
        if (supplier == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 팀입니다.");
        }
        return supplier.get();
    }
}
