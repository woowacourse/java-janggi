package piece;

import board.Board;
import movement.MovePaths;
import movement.Movement;
import movement.MovePath;
import position.Position;

import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {

    private static final MovePaths movePaths;
    private static final double DISTANCE;

    static {
        movePaths = new MovePaths(List.of(
                new MovePath(Movement.UP, Movement.RIGHT_UP),
                new MovePath(Movement.UP, Movement.LEFT_UP),
                new MovePath(Movement.DOWN, Movement.LEFT_DOWN),
                new MovePath(Movement.DOWN, Movement.RIGHT_DOWN),
                new MovePath(Movement.LEFT, Movement.LEFT_UP),
                new MovePath(Movement.LEFT, Movement.LEFT_DOWN),
                new MovePath(Movement.RIGHT, Movement.RIGHT_UP),
                new MovePath(Movement.RIGHT, Movement.RIGHT_DOWN)
        ));

        DISTANCE = movePaths.calculateDistance();
    }

    public Horse(final Position position, final Country country) {
        super(position, country);
    }

    // todo: 현재, board 인자 대신 함수형 인터페이스로 가능한지 여부 검토 중
    // todo: 하나의 인터페이스만 사용하면 가능한데, 3개 정도면 불가능 판단할 것
    // todo: depth 처리 잘하기
    @Override
    protected void validateFilter(Position src, Position destination, Board board) {
        List<Position> internalPositions = getInternalPositions(getInternalMovePaths(findCorrectMovePath(destination)));
        if (!board.isCorrectExistPositionCount(internalPositions, 0)) {
            throw new IllegalArgumentException("목적지까지의 경로 내부에 기물이 존재해서는 안됩니다.");
        }
    }

    private List<Position> getInternalPositions(MovePaths internalMoverPaths) {
        List<Position> positions = new ArrayList<>();
        Position buffer = new Position(position.x(), position.y());
        for (MovePath movePath : internalMoverPaths.getMovePaths()) {
            for (Movement movement : movePath.getMovements()) {
                buffer = buffer.move(movement);
            }
            positions.add(buffer);
        }
        return positions;
    }

    private MovePaths getInternalMovePaths(MovePath movePath) {
        return movePath.getInternalMovements();
    }

    private MovePath findCorrectMovePath(Position destination) {
        return movePaths.findCorrectMovePath(position, destination);
    }

    @Override
    protected double getDistance() {
        return DISTANCE;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Horse;
    }
}
