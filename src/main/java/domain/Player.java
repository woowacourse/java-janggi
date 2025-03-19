package domain;

import java.util.Objects;

public class Player {

    private final String name;
    private final TeamType teamType;

    public Player(String name, TeamType teamType) {
        this.name = name;
        this.teamType = teamType;
    }

    public boolean isSameTeam(TeamType teamType){
        return this.teamType.equals(teamType);
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
