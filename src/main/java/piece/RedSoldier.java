package piece;

import static location.Direction.DOWN;
import static location.Direction.LEFT;
import static location.Direction.RIGHT;

import game.Team;
import location.Direction;
import location.Position;
import java.util.List;

public class RedSoldier extends Piece {
    private static final List<Direction> VALID_STRAIGHT_DIRECTION = List.of(LEFT, RIGHT, DOWN);
    private static final Position DIAGONAL_POSSIBLE_POSITION = new Position(5, 9);
    private static final List<Position> VALID_PALACE_DIAGONAL_MOVEMENT = List.of(
            new Position(4, 10),
            new Position(6, 10));

    private Position currentPosition;

    public RedSoldier(int id, Team team, Position currentPosition) {
        super(id, team);
        this.currentPosition = currentPosition;
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.SOLDIER;
    }

    @Override
    public int getScore() {
        return 2;
    }

    @Override
    public void validateDestination(Position destination) {
        if (currentPosition.equals(DIAGONAL_POSSIBLE_POSITION)
                && Direction.isDiagonal(currentPosition, destination)) {
            checkValidDiagonalOneMovement(destination);
            return;
        }
        checkStraightForwardOneMovement(destination);
    }

    @Override
    public void validatePaths(Pieces pieces, Position destination) {

    }

    @Override
    public void updateCurrentPosition(Position destination) {
        currentPosition = destination;
    }

    @Override
    public boolean isPlacedAt(Position targetPosition) {
        return currentPosition.equals(targetPosition);
    }

    private void checkStraightForwardOneMovement(Position destination) {
        VALID_STRAIGHT_DIRECTION.stream()
                .filter(direction -> currentPosition.apply(direction).equals(destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다."));
    }

    private void checkValidDiagonalOneMovement(Position destination) {
        if (!VALID_PALACE_DIAGONAL_MOVEMENT.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 궁성 내 대각선 움직임입니다.");
        }
    }
}
