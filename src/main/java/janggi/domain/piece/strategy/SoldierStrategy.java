package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import java.util.List;

public class SoldierStrategy implements MoveStrategy {

    @Override
    public List<Position> findPath(Position from, Position to, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(from, to);

        camp.validateForwardDirection(directionInformation.rowDifference());
        validateSoldierMovement(directionInformation);

        return List.of(to);
    }

    private void validateSoldierMovement(DirectionInformation directionInformation) {
        if (directionInformation.calculateAbsRowDifference() + directionInformation.calculateAbsColDifference() != 1) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }
}
