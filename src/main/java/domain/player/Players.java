package domain.player;

import common.JanggiException;
import java.util.ArrayList;
import java.util.List;

public class Players {
    private static final String PLAYER_DUPLICATED = "플레이어는 중복될 수 없습니다.";
    private static final String PLAYER_LIMIT_EXCEEDED = "플레이어는 두 명을 초과할 수 없습니다.";
    private final List<Player> players;

    public Players() {
        this.players = new ArrayList<>();
    }

    public Players(List<Player> players) {
        List<Player> copiedPlayers = List.copyOf(players);
        validateSize(copiedPlayers);
        validateDuplicate(copiedPlayers);
        this.players = copiedPlayers;
    }

    private void validateSize(List<Player> players) {
        if (players.size() > 2) {
            throw new JanggiException(PLAYER_LIMIT_EXCEEDED);
        }
    }

    private void validateDuplicate(List<Player> players) {
        long distinctPlayerNameCount = players.stream()
                .map(Player::name)
                .distinct()
                .count();
        if (players.size() != distinctPlayerNameCount) {
            throw new JanggiException(PLAYER_DUPLICATED);
        }
    }

    public Players add(Player player) {
        List<Player> newPlayers = new ArrayList<>(players);
        newPlayers.add(player);
        return new Players(newPlayers);
    }

    public Player getByTeam(Team team) {
        return players.stream()
                .filter(player -> player.team() == team)
                .findAny()
                .orElseThrow(() -> new JanggiException("해당 팀의 플레이어가 없습니다."));
    }

    public String getChoPlayerName() {
        return getByTeam(Team.CHO).getNameValue();
    }

    public String getHanPlayerName() {
        return getByTeam(Team.HAN).getNameValue();
    }
}
