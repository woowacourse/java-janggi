package domain.piece;

import domain.Turn;
import domain.board.BoardStatus;
import domain.piece.strategy.MoveStrategy;
import domain.PieceId;
import domain.position.Position;
import java.util.Objects;

public abstract class Piece {
    private PieceId id;
    protected final MoveStrategy moveStrategy;
    private final PieceType pieceType;
    private final Team team;

    public Piece(PieceId id, MoveStrategy moveStrategy, PieceType pieceType, Team team) {
        this.id = id;
        this.moveStrategy = moveStrategy;
        this.pieceType = pieceType;
        this.team = team;
    }

    public Piece(MoveStrategy moveStrategy, PieceType pieceType, Team team) {
        this(null, moveStrategy, pieceType, team);
    }

    public void setId(PieceId id) {
        this.id = id;
    }

    public PieceId getId() {
        return id;
    }

    abstract public void check(BoardStatus boardStatus, Position start, Position destination);

    abstract public boolean isEmpty();

    public void validateTurn(Turn turn) {
        if (this.team != turn.turnOwnTeam()) {
            throw new IllegalArgumentException(PieceErrorMessage.NOT_YOUR_PIECE.getMessage());
        }
    }

    public void validateNotAlly(Piece destinationPiece) {
        if (!destinationPiece.isEmpty() && destinationPiece.isSameTeam(this)) {
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
