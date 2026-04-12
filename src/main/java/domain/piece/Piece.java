package domain.piece;

import domain.Board;
import domain.PieceExceptionMessage;
import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;
import domain.position.Position;
import java.util.List;
import java.util.Objects;

public abstract class Piece {
    protected final MoveStrategy moveStrategy;
    protected final List<MovementPolicy> movementPolicies;
    private final Team team;
    private final PieceType pieceType;
    private final double score;

    public Piece(MoveStrategy moveStrategy, List<MovementPolicy> movementPolicies, Team team, PieceType pieceType,
                 double score) {
        this.moveStrategy = moveStrategy;
        this.movementPolicies = movementPolicies;
        this.team = team;
        this.pieceType = pieceType;
        this.score = score;
    }

    public void movePolicy(Board pathContext, List<Position> movablePath, Position start, Position destination) {
        for (MovementPolicy movementPolicy : movementPolicies) {
            movementPolicy.validate(pathContext, movablePath, start, destination);
        }
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

    public double getScore() {
        return score;
    }
}
