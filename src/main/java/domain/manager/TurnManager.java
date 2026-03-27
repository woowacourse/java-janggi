package domain.manager;

import domain.player.Player;
import domain.player.Team;
import java.util.ArrayList;
import java.util.List;

public class TurnManager {

    private final List<Player> players;
    private boolean isGameRunning;

    public TurnManager(Player choPlayer, Player hanPlayer) {
        players = new ArrayList<>();
        players.add(choPlayer);
        players.add(hanPlayer);
        isGameRunning = true;
    }

    public Player getCurrentPlayer() {
        return players.getFirst();
    }

    public Team getCurrentTeam() {
        return players.getFirst().getTeam();
    }

    public void switchTurn() {
        Player player = players.removeFirst();
        players.add(player);
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void endGame() {
        isGameRunning = false;
    }
}
