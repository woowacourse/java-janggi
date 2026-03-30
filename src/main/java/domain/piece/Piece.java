package domain.piece;

import domain.BoardStatus;
import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;
import domain.position.Position;
import java.util.List;
import java.util.Objects;

public abstract class Piece {
    protected final MoveStrategy moveStrategy;
    protected final MovementPolicy movementPolicy;
    private final PieceType pieceType;
    private final Team team;

    public Piece(MoveStrategy moveStrategy, MovementPolicy movementPolicy, PieceType pieceType, Team team) {
        this.moveStrategy = moveStrategy;
        this.movementPolicy = movementPolicy;
        this.pieceType = pieceType;
        this.team = team;
    }

    public boolean canMoveWithRule(BoardStatus boardStatus, Position start, Position destination) {
        return movementPolicy.isMovable(boardStatus, findMovablePath(start, destination));
    }

    public List<Position> findMovablePath(Position start, Position destination) {
        return moveStrategy.findMovablePath(start, destination);
    }

    public boolean check(BoardStatus boardStatus, Position start, Position destination) {
        List<Position> movablePath = moveStrategy.findMovablePath(start, destination);
        return movementPolicy.isMovable(boardStatus, movablePath);
    }

    abstract public boolean jumpable();

    public boolean isSameTeam(Piece piece) {
        return team == piece.team;
    }

    public boolean isSameTeam(Team team) {
        return this.team == team;
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

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }

    abstract public boolean isEatable(Piece destinationPiece);
}
