package domain.player;

import domain.place.piece.Side;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Players {

    private static final int PLAYER_NAME_COUNT = 2;

    private final List<Player> players;

    private Players(List<Player> players) {
        this.players = List.copyOf(players);
    }

    public static Players from(List<String> names) {
        validateNonDuplicate(names);
        validateNameCount(names);

        List<Player> players = new ArrayList<>();
        players.add(new Player(names.get(0), Side.CHO));
        players.add(new Player(names.get(1), Side.HAN));

        return new Players(players);
    }

    private static void validateNonDuplicate(List<String> names) {
        Set<String> nameSet = new HashSet<>(names);

        if (nameSet.size() != names.size()) {
            throw new IllegalArgumentException("[ERROR] 플레이어 이름은 중복될 수 없습니다.");
        }
    }

    private static void validateNameCount(List<String> names) {
        if (names.size() != PLAYER_NAME_COUNT) {
            throw new IllegalArgumentException("[ERROR] 플레이어는 2명을 입력해야 합니다.");
        }
    }

    public Player getPlayer(Side side) {
        return players.stream()
                .filter(player -> player.getSide() == side)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 플레이어를 찾을 수 없습니다."));
    }
}
