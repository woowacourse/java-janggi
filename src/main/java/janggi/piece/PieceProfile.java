package janggi.piece;

import java.util.Objects;

public class PieceProfile {

    private final String name;
    private final Nation nation;

    public PieceProfile(final String name, final Nation nation) {
        this.name = name;
        this.nation = nation;
    }

    public boolean isCho() {
        return Nation.isCho(this.nation);
    }

    public boolean isHan() {
        return Nation.isHan(this.nation);
    }

    public String getName() {
        return name;
    }

    public Nation getNation() {
        return nation;
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
