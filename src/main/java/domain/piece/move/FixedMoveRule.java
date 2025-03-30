package domain.piece.move;

import domain.piece.move.area.MoveAreaConstraint;
import domain.position.Position;
import java.util.List;

public class FixedMoveRule implements MoveRule {

    private final List<Path> paths;
    private final MoveAreaConstraint moveAreaConstraint;

    public FixedMoveRule(List<Path> paths, MoveAreaConstraint moveAreaConstraint) {
        this.paths = paths;
        this.moveAreaConstraint = moveAreaConstraint;
    }

    @Override
    public List<Position> getIntermediatePath(Position from, Position to) {
        validateMoveArea(to);
        moveAreaConstraint.canMoveArea(to);
        Path reachablePath = getIntermediatePathByDirection(from, to);
        return reachablePath.findPathPositionsFrom(from);
    }

    private void validateMoveArea(Position to) {
        if (!moveAreaConstraint.canMoveArea(to)) {
            throw new IllegalArgumentException("지정된 지역으로 이동할 수 없는 기물입니다.");
        }
    }

    private Path getIntermediatePathByDirection(Position from, Position to) {
        return paths.stream()
                .filter(path -> path.canReachFromTo(from, to))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지정한 포지션으로 이동할 수 없습니다."));
    }
}
