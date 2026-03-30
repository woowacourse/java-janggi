package domain.piece;

import domain.PathContext;
import domain.PieceExceptionMessage;
import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;
import domain.position.Position;
import java.util.List;
import java.util.Objects;

public abstract class Piece {
    protected final MoveStrategy moveStrategy;
    protected final MovementPolicy movementPolicy;
    private final Team team;
    private final PieceType pieceType;

    public Piece(MoveStrategy moveStrategy, MovementPolicy movementPolicy, PieceType pieceType, Team team) {
        this.moveStrategy = moveStrategy;
        this.movementPolicy = movementPolicy;
        this.pieceType = pieceType;
        this.team = team;
    }

    public void movePolicy(PathContext pathContext) {
        movementPolicy.check(pathContext);
    }

    public List<Position> findMovablePath(Position start, Position destination) {
        return moveStrategy.findMovablePath(start, destination);
    }

    public boolean canBeJumpedOver() {
        return true;
    }

    public void capture(Piece target) {
        if (isSameTeam(target)) {
            throw new IllegalArgumentException(PieceExceptionMessage.DESTINATION_HAS_ALLY.getMessage());
        }
    }

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
}
