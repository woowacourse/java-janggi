package model.piece;

import model.Team;
import model.coordinate.Displacement;
import model.coordinate.Palace;
import model.coordinate.Position;

public class Soldier extends Piece {

    private final int forwardDirection;

    public Soldier(Team team) {
        super(team, PieceType.SOLDIER);
        this.forwardDirection = resolveForwardDirection(team);
    }

    private static int resolveForwardDirection(Team team) {
        if (team.isHan()) {
            return 1;
        }
        return -1;
    }

    @Override
    protected void validateMove(Position current, Position next) {
        Displacement displacement = next.toDisplacement(current);
        if (displacement.isNotStraight()) {
            validateDiagonalOneStep(displacement);
            validateEnemyPalaceDiagonal(current, next);
            return;
        }
        validateForwardOneStep(displacement);
    }

    private void validateEnemyPalaceDiagonal(Position current, Position next) {
        if (isNotInEnemyPalaceDiagonal(current, next)) {
            throw new IllegalArgumentException("졸은 궁성 교차점에서만 대각선 이동 가능합니다.");
        }
    }

    private boolean isNotInEnemyPalaceDiagonal(Position current, Position next) {
        Team enemy = team().opposite();
        return !Palace.isDiagonalPoint(enemy, current) || !Palace.isDiagonalPoint(enemy, next);
    }

    private void validateForwardOneStep(Displacement displacement) {
        if (!(displacement.isForwardBy(forwardDirection) || displacement.isSideOneStep())) {
            throw new IllegalArgumentException("졸은 전진 또는 좌우로만 이동할 수 있습니다.");
        }
    }

    private void validateDiagonalOneStep(Displacement displacement) {
        if (!displacement.isOneStepInRange()) {
            throw new IllegalArgumentException("졸은 한 칸만 이동할 수 있습니다.");
        }

        if (!displacement.isSameForwardDirection(forwardDirection)) {
            throw new IllegalArgumentException("졸은 전진 대각선만 이동 가능합니다.");
        }
    }
}
