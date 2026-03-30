package domain.piece;

import domain.ErrorMessage;
import domain.Offset;
import domain.Path;

import java.util.List;
import java.util.Objects;

public abstract class Piece {
    private final PieceType pieceType;
    private final Team team;

    public Piece(PieceType pieceType, Team team) {
        this.pieceType = pieceType;
        this.team = team;
    }

    public boolean isSameTeam(Piece another) {
        return another.team == team;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isCannon() {
        return false;
    }

    abstract public List<Offset> getPathPositions(Offset offset);

    public void canMove(List<Path> paths, Piece to) {
        if (!paths.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.PATH_BLOCKED.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return pieceType == piece.pieceType && team == piece.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, team);
    }
}
