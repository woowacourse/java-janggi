package domain.piece;

import domain.BoardLocation;
import domain.Team;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final Team team;

    public Piece(PieceType pieceType, Team team) {
        this.pieceType = pieceType;
        this.team = team;
    }

    public abstract boolean isMovable(BoardLocation current, BoardLocation target);

    public abstract List<BoardLocation> createAllPath(BoardLocation current, BoardLocation target);

    public abstract boolean canArrive(List<Piece> pathPiece);

    public abstract boolean canDestination(Piece destinationPiece);

    public boolean isNotSameType(Piece piece) {
        return this.pieceType != piece.pieceType;
    }

    public boolean isEqualTeam(Team team) {
        return this.team == team;
    }

    public boolean isEqualTeam(Piece piece) {
        return this.team == piece.team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Piece piece)) {
            return false;
        }
        return pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pieceType);
    }
}
