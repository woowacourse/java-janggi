package domain.player;

import domain.piece.Team;

public record Player(Name name, Team team) {

    public static Player of(String playerName, Team team) {
        return new Player(Name.of(playerName), team);
    }
}
