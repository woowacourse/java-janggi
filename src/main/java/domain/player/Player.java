package domain.player;

import domain.TeamType;
import java.util.Objects;

public class Player {

    private final Username name;
    private final TeamType teamType;

    public Player(Username name, TeamType teamType) {
        this.name = name;
        this.teamType = teamType;
    }

    public boolean isSameTeam(TeamType teamType) {
        return this.teamType.equals(teamType);
    }

    public String getName() {
        return name.getName();
    }

    public TeamType getTeamType() {
        return teamType;
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
        return Objects.equals(name, player.name) && teamType == player.teamType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, teamType);
    }
}
