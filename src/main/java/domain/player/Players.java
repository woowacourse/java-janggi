package domain.player;

import domain.Team;
import java.util.List;

public class Players {
    List<Player> players;

    public Players(List<Player> players) {
        this.players = players;
    }

    public String getNameFirstPlayer() {
        return players.getFirst().getName();
    }

    public String getNameSecondPlayer() {
        return players.getLast().getName();
    }

    public Player getThisTurnPlayer(int sequence) {
        return players.get(sequence);
    }

    public Player getPlayerByTeam(Team team) {
        return players.stream()
                .filter(player -> player.getTeam() == team)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 팀이 없습니다."));
    }
}
