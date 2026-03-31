package domain.player;

import common.exception.JanggiException;
import java.util.ArrayList;
import java.util.List;

public class Players {
    public static final String PLAYER_DUPLICATED = "플레이어는 중복될 수 없습니다.";
    public static final String PLAYER_LIMIT_EXCEEDED = "플레이어는 두 명을 초과할 수 없습니다.";
    private final List<Player> players;

    public Players() {
        this.players = new ArrayList<>();
    }

    public Players(List<Player> players) {
        validateSize(players);
        validateDuplicate(players);
        this.players = players;
    }

    private void validateSize(List<Player> players) {
        if (players.size() > 2) {
            throw new JanggiException(PLAYER_LIMIT_EXCEEDED);
        }
    }

    private void validateDuplicate(List<Player> players) {
        long distinctPlayerNameCount = players.stream()
                .map(Player::getName)
                .distinct()
                .count();
        if (players.size() != distinctPlayerNameCount) {
            throw new JanggiException(PLAYER_DUPLICATED);
        }
    }

    public Players add(Player player) {
        players.add(player);
        return new Players(this.players);
    }

    public Player getFirst() {
        return players.getFirst();
    }

    public Player removeFirst() {
        return players.removeFirst();
    }
}
