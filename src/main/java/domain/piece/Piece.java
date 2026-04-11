package domain.piece;

import domain.board.PathPieces;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;

import java.util.Objects;

public abstract class Piece implements MovablePiece {

    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    @Override
    public abstract Path calculatePath(Position source, Position destination);

    @Override
    public abstract boolean validatePath(PathPieces pathPieces);

    @Override
    public boolean isDifferentTeam(BasicPiece other) {
        if (other.isNone()) {
            return true;
        }
        return this.team != other.getTeam();
    }

    @Override
    public boolean isDifferentTeam(Team team) {
        return this.team != team;
    }

    @Override
    public boolean isType(PieceType type) {
        return this.pieceType == type;
    }

    @Override
    public boolean isNone() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return team == piece.team &&
                pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, pieceType);
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
