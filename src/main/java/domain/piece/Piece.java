package domain.piece;

import domain.BoardLocation;
import domain.Team;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    protected final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public abstract boolean isMovable(BoardLocation current, BoardLocation target);

    public abstract List<BoardLocation> createAllPath(BoardLocation current, BoardLocation target);

    public abstract boolean canArrive(List<Piece> pathPiece);

    public abstract boolean canDestination(Piece destinationPiece);

    public abstract PieceType getType();

    public final boolean isNotSameType(Piece piece) {
        return !Objects.equals(this.getType(), piece.getType());
    }

    public final boolean isEqualTeam(Team team) {
        return this.team == team;
    }

    public final boolean isEqualTeam(Piece piece) {
        return this.team == piece.team;
    }

    public final Team getTeam() {
        return this.team;
    }
}
