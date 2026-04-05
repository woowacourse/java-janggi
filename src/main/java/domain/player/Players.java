package domain.player;

import domain.place.piece.Side;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Players {

    private final List<Player> players;

    private Players(List<Player> players) {
        this.players = players;
    }

    public static Players from(List<String> names) {
        validateNonDuplicate(names);

        List<Player> players = new ArrayList<>();
        players.add(new Player(names.get(0), Side.CHO));
        players.add(new Player(names.get(1), Side.HAN));

        return new Players(players);
    }

    private static void validateNonDuplicate(List<String> names) {
        if (new HashSet<>(names).size() != names.size()) {
            throw new IllegalArgumentException("[ERROR] 플레이어 이름은 중복될 수 없습니다.");
        }
    }

    public Player getPlayerBySide(Side side) {
        return players.stream()
                .filter(player -> player.getSide().equals(side))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[Error] 일치하는 나라(초/한)의 플레이어가 없습니다"));
    }
}
