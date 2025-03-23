package domain.piece;

import domain.BoardLocation;
import domain.Team;
import java.util.List;
import java.util.Objects;

public class Piece {

    private final PieceType pieceType;
    private final Team team;

    public Piece(PieceType pieceType, Team team) {
        this.pieceType = pieceType;
        this.team = team;
    }

    public boolean isMovable(BoardLocation current, BoardLocation target) {
        return pieceType.isMovable(current, target);
    }

    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation target) {
        return pieceType.createAllPath(current, target);
    }

    public boolean isSameType(Piece piece) {
        return this.pieceType == piece.pieceType;
    }

    public boolean isSameType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }


    public String toString() {
        return pieceType.toString();
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

    public boolean isEqualTeam(Team team) {
        return this.team == team;
    }

    public boolean isEqualTeam(Piece piece) {
        return this.team == piece.team;
    }

    public boolean canArrive(List<Piece> pathPiece) {
        return pieceType.canArrive(pathPiece);
    }

    public boolean canDestination(Piece destinationPiece) {
        return pieceType.canDestination(this, destinationPiece);
    }
}
