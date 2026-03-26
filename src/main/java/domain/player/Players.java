package domain.player;

import domain.place.piece.Side;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Players {

    private final Map<Side, Player> players;

    private Players(Map<Side, Player> players) {
        this.players = new EnumMap<>(players);
    }

    public static Players from(List<String> names) {
        validateNonDuplicate(names);

        Map<Side, Player> players = new EnumMap<>(Side.class);
        players.put(Side.CHO, new Player(names.get(0), Side.CHO));
        players.put(Side.HAN, new Player(names.get(1), Side.HAN));

        return new Players(players);
    }

    private static void validateNonDuplicate(List<String> names) {
        Set<String> s = new HashSet<>(names);

        if (s.size() != names.size()) {
            throw new IllegalArgumentException("[ERROR] 플레이어 이름은 중복될 수 없습니다.");
        }
    }

    public Player getPlayerBySide(Side side) {
        return players.get(side);
    }
}
