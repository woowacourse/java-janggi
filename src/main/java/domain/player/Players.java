package domain.player;

import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Players {
    private final Player bluePlayer;
    private final Player redPlayer;
    private final List<Player> players = new ArrayList<>();

    public Players(Player bluePlayer, Player redPlayer) {
        this.bluePlayer = bluePlayer;
        this.redPlayer = redPlayer;
        players.add(bluePlayer);
        players.add(redPlayer);
    }

    public String getBluePlayerName() {
        return bluePlayer.getName();
    }

    public String getRedPlayerName() {
        return redPlayer.getName();
    }

    public Player getThisTurnPlayer(int sequence) {
        return players.get(sequence);
    }

    public Player getBluePlayer() {
        return bluePlayer;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getPlayerByTeam(Team team) {
        return players.stream()
                .filter(player -> player.getTeam() == team)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 팀이 없습니다."));
    }

    public int getBluePlayerId() {
        return bluePlayer.getId();
    }

    public int getRedPlayerId() {
        return redPlayer.getId();
    }
}
