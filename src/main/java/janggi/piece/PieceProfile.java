package janggi.piece;

import java.util.Objects;

public class PieceProfile {

    private final String name;
    private final Team team;

    public PieceProfile(final String name, final Team team) {
        this.name = name;
        this.team = team;
    }

    public boolean isCho() {
        return Team.isCho(this.team);
    }

    public boolean isHan() {
        return Team.isHan(this.team);
    }

    public String getName() {
        return name;
    }

    public Team getNation() {
        return team;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PieceProfile that = (PieceProfile) o;
        return Objects.equals(getName(), that.getName()) && getNation() == that.getNation();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNation());
    }
}
