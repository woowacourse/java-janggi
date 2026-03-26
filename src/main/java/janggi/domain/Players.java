package janggi.domain;

import java.util.HashSet;
import java.util.Set;

public class Players {

    private static final int REQUIRED_PLAYER_COUNT = 2;

    private final Set<Player> players;
    private Turn turn;

    private Players(Set<Player> players) {
        this.players = players;
        this.turn = new Turn();
    }

    public static Players from(String choPlayerName, String hanPlayerName) {
        Player choPlayer = new Player(choPlayerName, Side.CHO);
        Player hanPlayer = new Player(hanPlayerName, Side.HAN);
        Set<Player> players = Set.of(choPlayer, hanPlayer);
        validateDuplicatedPlayers(players);
        return new Players(players);
    }

    public static void validateDuplicatedPlayers(Set<Player> players) {
        if (players.size() < REQUIRED_PLAYER_COUNT) {
            throw new IllegalArgumentException("[ERROR] 플레이어는 중복된 이름을 가질 수 없습니다.");
        }
    }

    public Player findBySide(Side side) {
        return players.stream()
                .filter(player -> player.getSide() == side)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 진영의 플레이어가 존재하지 않습니다."));
    }
}
