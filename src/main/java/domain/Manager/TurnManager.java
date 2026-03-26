package domain.Manager;

import domain.player.Player;
import java.util.ArrayList;
import java.util.List;

public class TurnManager {

    private final List<Player> players;

    public TurnManager(Player choPlayer, Player hanPlayer) {
        players = new ArrayList<>();
        players.add(choPlayer);
        players.add(hanPlayer);
    }

    public Player currentTurn() {
        return players.getFirst();
    }

    public void switchTurn() {
        Player player = players.removeFirst();
        players.add(player);
    }
}
