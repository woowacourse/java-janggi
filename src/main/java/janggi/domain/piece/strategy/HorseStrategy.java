package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import java.util.ArrayList;
import java.util.List;

public class HorseStrategy implements MoveStrategy {

    @Override
    public List<Position> findPath(Position from, Position to, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(from, to);

        validateHorseMovement(directionInformation);

        if (directionInformation.isRowBiggerThanCol()) {
            return createRowFirstPath(from, directionInformation);
        }
        return createColFirstPath(from, directionInformation);
    }

    private List<Position> createRowFirstPath(Position from, DirectionInformation directionInformation) {
        List<Position> path = new ArrayList<>();

        int rowDirection = directionInformation.calculateRowDirection();
        int colDirection = directionInformation.calculateColDirection();
        from = from.moveRow(rowDirection);
        path.add(from);

        from = from.moveDiagonal(rowDirection, colDirection);
        path.add(from);
        return path;
    }

    private List<Position> createColFirstPath(Position from, DirectionInformation directionInformation) {
        List<Position> path = new ArrayList<>();

        int rowDirection = directionInformation.calculateRowDirection();
        int colDirection = directionInformation.calculateColDirection();
        from = from.moveCol(colDirection);
        path.add(from);

        from = from.moveDiagonal(rowDirection, colDirection);
        path.add(from);
        return path;
    }

    private void validateHorseMovement(DirectionInformation directionInformation) {
        if ((directionInformation.rowDifference() != 1 || directionInformation.colDifference() != 2)
                && (directionInformation.rowDifference() != 2 || directionInformation.colDifference() != 1)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }
}
