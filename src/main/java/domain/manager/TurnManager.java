package domain.manager;

import domain.player.Player;
import domain.player.Players;
import domain.player.Team;

public class TurnManager {

    private final Players players;
    private boolean isGameRunning;

    public TurnManager(Player choPlayer, Player hanPlayer) {
        players = new Players();
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
