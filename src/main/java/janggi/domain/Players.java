package janggi.domain;

import java.util.List;

public class Players {
    
    private final List<Player> players;

    public Players(List<Player> players) {
        validatePlayers(players);
        this.players = players;
    }

    private void validatePlayers(List<Player> players) {
        if(players.size() != 2) {
            throw new IllegalArgumentException("플레이어는 두 명이어야 합니다.");
        }

        List<Player> distinctPlayers = players.stream().distinct().toList();
        if(distinctPlayers.size() < 2) {
            throw new IllegalArgumentException("플레이어는 중복될 수 없습니다.");
        }
    }
}
