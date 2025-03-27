package domain.piece.path;

import domain.position.Direction;
import domain.position.Position;
import java.util.List;

public class FixedSingleMovePathFinder implements PathFinder{

    private final List<Direction> directions;

    public FixedSingleMovePathFinder(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public List<Position> findIntermediatePositions(Position from, Position to) {
        validateDestination(from, to);
        return List.of();
    }

    private void validateDestination(Position from,Position to){
        boolean canNotMove = directions.stream()
                .filter(direction -> from.canMovePosition(direction.getDeltaRow(), direction.getDeltaColumn()))
                .map(direction -> from.movePosition(direction.getDeltaRow(), direction.getDeltaColumn()))
                .noneMatch(position -> position.equals(to));
        if(canNotMove){
            throw new IllegalArgumentException("해당 좌표로 이동시킬 수 없습니다.");
        }
    }
}
