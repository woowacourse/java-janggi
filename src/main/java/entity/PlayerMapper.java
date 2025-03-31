package entity;

import domain.Player;
import domain.Team;

public class PlayerMapper {
    public static Player toPlayer(Team team) {
        return new Player(team);
    }
}
