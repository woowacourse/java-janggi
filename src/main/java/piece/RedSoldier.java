package piece;

import static location.Direction.DOWN;
import static location.Direction.LEFT;
import static location.Direction.RIGHT;

import location.Direction;
import location.Position;
import java.util.List;
import store.Pieces;

public class RedSoldier implements Piece {
    private static final List<Direction> RED_SOLDIER_PATH_INFO = List.of(LEFT, RIGHT, DOWN);

    private final Position currentPosition;

    public RedSoldier(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public void validateDestination(Position destination) {
        RED_SOLDIER_PATH_INFO.stream()
                .filter(direction -> currentPosition.apply(direction).equals(destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다."));
    }

    @Override
    public void validatePaths(Pieces pieces, Position destination) {

    }

    @Override
    public Piece move(Position destination) {
        return new RedSoldier(destination);
    }

    @Override
    public boolean isPlacedAt(Position targetPosition) {
        return currentPosition.equals(targetPosition);
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.SOLIDER;
    }
}
