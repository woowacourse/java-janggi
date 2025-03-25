package domain.player;

import domain.Team;
import domain.piece.Piece;
import java.util.Objects;

public class Player {

    private final String name;
    private final Team team;


    public Player(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public boolean isTeam(Piece piece) {
        return team == piece.getTeam();
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(name, player.name) && team == player.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, team);
    }
}
