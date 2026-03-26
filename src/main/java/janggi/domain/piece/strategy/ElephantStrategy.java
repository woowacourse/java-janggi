package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import java.util.ArrayList;
import java.util.List;

public class ElephantStrategy implements MoveStrategy {

    @Override
    public List<Position> findPath(Position from, Position to, Camp camp) {
        DirectionInformation directionInfo = new DirectionInformation(from, to);

        validateElephantMovement(directionInfo);

        if (directionInfo.isRowBiggerThanCol()) {
            return createRowFirstPath(from, directionInfo);
        }
        return createColFirstPath(from, directionInfo);
    }

    private void validateElephantMovement(DirectionInformation directionInfo) {
        if ((directionInfo.rowDifference() != 2 || directionInfo.colDifference() != 3)
                && (directionInfo.rowDifference() != 3 || directionInfo.colDifference() != 2)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }

    private List<Position> createRowFirstPath(Position from, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();

        from = from.moveRow(directionInfo.calculateRowDirection());
        path.add(from);

        path.addAll(moveDiagonal(from, directionInfo));
        return path;
    }

    private List<Position> createColFirstPath(Position from, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();

        from = from.moveCol(directionInfo.calculateColDirection());
        path.add(from);

        path.addAll(moveDiagonal(from, directionInfo));
        return path;
    }

    private List<Position> moveDiagonal(Position from, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            from = from.moveDiagonal(directionInfo.calculateRowDirection(), directionInfo.calculateColDirection());
            path.add(from);
        }
        return path;
    }
}
