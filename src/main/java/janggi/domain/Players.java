package janggi.domain;

import janggi.dto.PlayerDTO;
import java.util.Set;

public class Players {

    private final Set<Player> players;
    private final Turn turn;

    private Players(Set<Player> players) {
        this.players = players;
        this.turn = new Turn();
    }

    public static Players from(String choPlayerName, String hanPlayerName) {
        validateDuplicatedNames(choPlayerName, hanPlayerName);
        Player choPlayer = new Player(choPlayerName, Side.CHO);
        Player hanPlayer = new Player(hanPlayerName, Side.HAN);

        return new Players(Set.of(choPlayer, hanPlayer));
    }

    private static void validateDuplicatedNames(String choPlayerName, String hanPlayerName) {
        if (choPlayerName.equals(hanPlayerName)) {
            throw new IllegalArgumentException("[ERROR] 플레이어는 중복된 이름을 가질 수 없습니다.");
        }
    }

    public PlayerDTO getCurrentPlayer() {
        return players.stream()
                .filter(player -> player.isMyTurn(turn))
                .findFirst()
                .map(Player::mapToVO)
                .orElseThrow(() -> new IllegalStateException("[ERROR] 현재 턴에 해당하는 플레이어가 없습니다."));
    }

    public void switchTurn() {
        turn.switchTurn();
    }
}
