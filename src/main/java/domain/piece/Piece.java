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

    abstract public boolean isEmpty();

    public void validateTurn(Team turn) {
        if (this.team != turn) {
            throw new IllegalArgumentException(PieceErrorMessage.NOT_YOUR_PIECE.getMessage());
        }
    }

    public void validateNotAlly(Piece destinationPiece) {
        if (destinationPiece.isEmpty() && destinationPiece.isSameTeam(this)) {
            throw new IllegalArgumentException(PieceErrorMessage.ALREADY_OCCUPIED_BY_ALLY.getMessage());
        }
    }

    public boolean isSameTeam(Piece other) {
        if (other == null) {
            return false;
        }
        return this.team == other.team;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
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
        return pieceType == piece.pieceType && team == piece.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, team);
    }
}
