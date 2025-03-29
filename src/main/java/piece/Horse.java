package piece;

import static location.Direction.DOWN;
import static location.Direction.LEFT;
import static location.Direction.RIGHT;
import static location.Direction.UP;

import game.Team;
import java.util.Collections;
import location.Direction;
import location.Distance;
import location.Position;
import java.util.List;
import java.util.Map;

public class Horse implements Piece {
    private static final Map<Distance, List<Direction>> HORSE_PATH_INFO = Map.of(
            new Distance(-1, -2), List.of(UP),
            new Distance(1, -2), List.of(UP),
            new Distance(-2, -1), List.of(LEFT),
            new Distance(-2, 1), List.of(LEFT),
            new Distance(-1, 2), List.of(DOWN),
            new Distance(1, 2), List.of(DOWN),
            new Distance(2, -1), List.of(RIGHT),
            new Distance(2, 1), List.of(RIGHT)
    );

    private final Integer pieceId;
    private final Team team;
    private boolean isCatch;
    private final PieceType pieceType;
    private Position currentPosition;

    public Horse(int pieceId, Team team, Position currentPosition) {
        this.pieceId = pieceId;
        this.team = team;
        this.isCatch = false;
        this.pieceType = PieceType.HORSE;
        this.currentPosition = currentPosition;
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
        boolean isOtherPieceExistedInPaths = findPathsBy(distance).stream()
                .anyMatch(direction -> pieces.isContainedPieceAtPosition(
                        currentPosition.apply(direction)));
        if (isOtherPieceExistedInPaths) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }

    @Override
    public void move(Position destination) {
        currentPosition = destination;
    }

    @Override
    public void catchByOpponent() {
        isCatch = true;
    }

    @Override
    public boolean isPlacedAt(Position targetPosition) {
        return currentPosition.equals(targetPosition);
    }

    @Override
    public int getId() {
        return pieceId;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public int getScore() {
        return pieceType.getScore();
    }

    @Override
    public boolean isCatch() {
        return isCatch;
    }

    private List<Direction> findPathsBy(Distance distance) {
        return HORSE_PATH_INFO.getOrDefault(distance, Collections.emptyList());
    }
}
