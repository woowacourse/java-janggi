package domain.rule;

import domain.position.Palace;
import domain.position.Position;
import java.util.List;

public class PalaceDiagonalStraightRule implements MoveRule {

    @Override
    public boolean canMove(Position source, Position target) {
        return Palace.all().stream()
                .anyMatch(palace -> palace.isDiagonalStraight(source, target));
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        return Palace.all().stream()
                .filter(palace -> palace.isDiagonalStraight(source, target))
                .findFirst()
                .map(palace -> palace.diagonalRoute(source, target))
                .orElse(List.of());
    }
}
