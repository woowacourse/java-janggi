package piece;

import static location.Direction.DOWN;
import static location.Direction.DOWN_LEFT_DIAGONAL;
import static location.Direction.DOWN_RIGHT_DIAGONAL;
import static location.Direction.LEFT;
import static location.Direction.RIGHT;
import static location.Direction.UP;
import static location.Direction.UP_LEFT_DIAGONAL;
import static location.Direction.UP_RIGHT_DIAGONAL;

import game.Team;
import java.util.Collections;
import location.Direction;
import location.Distance;
import location.Position;

import java.util.List;
import java.util.Map;

public class Elephant extends Piece {
    public static final Map<Distance, List<Direction>> ELEPHANT_PATH_INFO = Map.of(
            new Distance(-2, -3), List.of(UP, UP_LEFT_DIAGONAL),
            new Distance(2, -3), List.of(UP, UP_RIGHT_DIAGONAL),
            new Distance(-3, -2), List.of(LEFT, UP_LEFT_DIAGONAL),
            new Distance(-3, 2), List.of(LEFT, DOWN_LEFT_DIAGONAL),
            new Distance(-2, 3), List.of(DOWN, DOWN_LEFT_DIAGONAL),
            new Distance(2, 3), List.of(DOWN, DOWN_RIGHT_DIAGONAL),
            new Distance(3, -2), List.of(RIGHT, UP_RIGHT_DIAGONAL),
            new Distance(3, 2), List.of(RIGHT, DOWN_RIGHT_DIAGONAL)
    );

    private Position currentPosition;

    public Elephant(int id, Team team, Position currentPosition) {
        super(id, team);
        this.currentPosition = currentPosition;
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ELEPHANT;
    }

    @Override
    public int getScore() {
        return 3;
    }

    @Override
    public void validateDestination(Position destination) {
        Distance distance = Distance.createBy(currentPosition, destination);
        if (findPathsBy(distance).isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    @Override
    public void validatePaths(Pieces pieces, Position destination) {
        Distance distance = Distance.createBy(currentPosition, destination);
        boolean isOtherPieceExistedInPaths = isOtherPieceExistedInPaths(pieces, distance);
        if (isOtherPieceExistedInPaths) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }

    @Override
    public void updateCurrentPosition(Position destination) {
        currentPosition = destination;
    }

    @Override
    public boolean isPlacedAt(Position targetPosition) {
        return currentPosition.equals(targetPosition);
    }

    private List<Direction> findPathsBy(Distance distance) {
        return ELEPHANT_PATH_INFO.getOrDefault(distance, Collections.emptyList());
    }

    private boolean isOtherPieceExistedInPaths(Pieces pieces, Distance distance) {
        return findPathsBy(distance).stream()
                .anyMatch(direction -> pieces.isContainedPieceAtPosition(
                        currentPosition.apply(direction)));
    }
}
