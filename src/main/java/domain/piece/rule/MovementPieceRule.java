package domain.piece.rule;

import domain.coordination.Coordination;
import domain.move.Movement;
import domain.piece.Piece;
import domain.piece.Team;
import domain.piece.error.PieceException;
import java.util.List;
import java.util.Optional;

public abstract class MovementPieceRule implements PieceRule {

    protected static final String IMPOSSIBLE_MOVE = "기물이 움직일 수 없는 위치입니다.";

    private final List<Movement> movements;

    protected MovementPieceRule(List<Movement> movements) {
        this.movements = movements;
    }

    @Override
    public void validate(Coordination from, Coordination to, Team team) {
        movableMovement(from, to, team)
                .orElseThrow(() -> new PieceException(IMPOSSIBLE_MOVE));
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to, Team team) {
        return movableMovement(from, to, team)
                .map(movement -> movement.path(from, to))
                .orElse(List.of());
    }

    private Optional<Movement> movableMovement(Coordination from, Coordination to, Team team) {
        return movements.stream()
                .filter(movement -> movement.canMove(from, to, team))
                .findFirst();
    }

    @Override
    public void validatePath(List<Piece> piecesOnPath) {
        if (!piecesOnPath.isEmpty()) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }
}
