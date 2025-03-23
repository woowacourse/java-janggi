package player;

import java.util.List;

public class Players {
    private final List<Player> players;

    public Players(List<Player> players) {
        this.players = players;
    }

    public boolean isKingDie() {
        return players.stream()
                .anyMatch(Player::isKingDie);
    }
}
