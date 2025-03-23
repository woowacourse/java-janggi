package domain.piece;

import domain.BoardLocation;
import domain.Team;
import java.util.List;

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

    public boolean isNotSameType(Piece piece) {
        return this != piece;
    }

    public boolean isEqualTeam(Team team) {
        return this.team == team;
    }

    public boolean isEqualTeam(Piece piece) {
        return this.team == piece.team;
    }
}
