package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.Objects;

public class Soldier implements Piece {

    private final Team team;
    private final Position position;

    public Soldier(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Soldier soldier = (Soldier) o;
        return team == soldier.team && Objects.equals(position, soldier.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position);
    }
}
