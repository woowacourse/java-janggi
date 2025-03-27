package domain.piece.path;

import domain.position.Direction;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FixedSingleMovePathFinder implements PathFinder{
    private static final Map<Position,List<Direction>> PALACE_MOVEMENT;

    static {
        PALACE_MOVEMENT = Map.of(
                Position.of(0,3), List.of(Direction.RIGHT_UP),
                Position.of(0,5), List.of(Direction.LEFT_UP),
                Position.of(2,3), List.of(Direction.RIGHT_DOWN),
                Position.of(2,5), List.of(Direction.LEFT_DOWN),
                Position.of(1,4), List.of(Direction.RIGHT_UP,Direction.LEFT_UP,Direction.RIGHT_DOWN,Direction.LEFT_DOWN),
                Position.of(7,3), List.of(Direction.RIGHT_UP),
                Position.of(7,5), List.of(Direction.LEFT_UP),
                Position.of(9,3), List.of(Direction.RIGHT_DOWN),
                Position.of(9,5), List.of(Direction.LEFT_DOWN),
                Position.of(8,4), List.of(Direction.RIGHT_UP,Direction.LEFT_UP,Direction.RIGHT_DOWN,Direction.LEFT_DOWN)
        );
    }

    private final List<Direction> directions;

    public FixedSingleMovePathFinder(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public List<Position> findIntermediatePositions(Position from, Position to) {
        List<Direction> possibleDirections = new ArrayList<>(directions);
        if(PALACE_MOVEMENT.containsKey(from)){
            possibleDirections.addAll(PALACE_MOVEMENT.get(from));
        }
        validateDestination(from, to, possibleDirections);
        return List.of();
    }

    private void validateDestination(Position from,Position to, List<Direction> possibleDirections){
        boolean canNotMove = possibleDirections.stream()
                .filter(direction -> from.canMovePosition(direction.getDeltaRow(), direction.getDeltaColumn()))
                .map(direction -> from.movePosition(direction.getDeltaRow(), direction.getDeltaColumn()))
                .noneMatch(position -> position.equals(to));
        if(canNotMove){
            throw new IllegalArgumentException("해당 좌표로 이동시킬 수 없습니다.");
        }
    }
}
