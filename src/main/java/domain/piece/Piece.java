package domain.piece;

import domain.BoardStatus;
import domain.piece.strategy.MoveStrategy;
import domain.position.Position;
import java.util.Objects;

public abstract class Piece {
    protected final MoveStrategy moveStrategy;
    private final PieceType pieceType;
    private final Team team;

    public Piece(MoveStrategy moveStrategy, PieceType pieceType, Team team) {
        this.moveStrategy = moveStrategy;
        this.pieceType = pieceType;
        this.team = team;
    }

    abstract public void check(BoardStatus boardStatus, Position start, Position destination);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return pieceType == piece.pieceType && team == piece.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, team);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }

    public void validateTurn(Team turn) {
        if (this.team != turn) {
            throw new IllegalArgumentException("아군만 이동할 수 있습니다.");
        }
    }

    public void validateNotAlly(Piece destinationPiece) {
        if (destinationPiece != null && destinationPiece.isSameTeam(this)) {
            throw new IllegalArgumentException("이동할 수 없습니다. (목적지에 아군이 존재함)");
        }
    }

    public boolean isSameTeam(Piece other) {
        if (other == null) {
            return false;
        }
        return this.team == other.team;
    }
}
