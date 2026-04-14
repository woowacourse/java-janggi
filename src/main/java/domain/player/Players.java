package domain.player;

import java.util.ArrayList;
import java.util.List;

public class Players {

    private final List<Player> players;

    public Players(final List<Player> players) {
        this.players = new ArrayList<>(players);
    }

    public Player findByTeam(final Team team) {
        return players.stream()
                .filter(player -> player.getTeam() == team)
                .findFirst()
                .orElseThrow();
    }

    public List<Player> getPlayers() {
        return players;
    }
}
