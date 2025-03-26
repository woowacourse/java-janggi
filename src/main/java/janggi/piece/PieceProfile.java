package janggi.piece;

import java.util.Objects;

public class PieceProfile {

    private final PieceType pieceType;
    private final Team team;

    public PieceProfile(final PieceType pieceType, final Team team) {
        this.pieceType = pieceType;
        this.team = team;
    }

    public boolean isCho() {
        return Team.isCho(this.team);
    }

    public boolean isHan() {
        return Team.isHan(this.team);
    }

    public PieceType getPieceType() {
        return pieceType;
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
        return getPieceType() == that.getPieceType() && team == that.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPieceType(), team);
    }
}
