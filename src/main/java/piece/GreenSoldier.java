package piece;

import static location.Direction.LEFT;
import static location.Direction.RIGHT;
import static location.Direction.UP;

import java.util.Map;
import location.Direction;
import location.PathUtility;
import location.Position;
import java.util.List;
import store.Pieces;

public class GreenSoldier implements Piece {
    private static final List<Direction> VALID_STRAIGHT_DIRECTION = List.of(LEFT, RIGHT, UP);
    private static final Position DIAGONAL_POSSIBLE_POSITON = new Position(5, 2);
    private static final List<Position> VALID_PALACE_DIAGONAL_MOVEMENT = List.of(new Position(4, 1), new Position(6, 3));

    private final PieceType pieceType;
    private final Position currentPosition;

    public GreenSoldier(Position currentPosition) {
        this.pieceType = PieceType.SOLIDER;
        this.currentPosition = currentPosition;
    }

    @Override
    public void validateDestination(Position destination) {
        if (currentPosition.equals(DIAGONAL_POSSIBLE_POSITON)
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
    public Piece move(Position destination) {
        return new GreenSoldier(destination);
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
        return pieceType;
    }

    @Override
    public int getScore() {
        return pieceType.getScore();
    }

    private void checkStraightForwardOneMovement(Position destination) {
        VALID_STRAIGHT_DIRECTION.stream()
                .filter(direction -> currentPosition.apply(direction).equals(destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다."));
    }

    private void checkValidDiagonalOneMovement(Position destination) {
        if(!VALID_PALACE_DIAGONAL_MOVEMENT.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 궁성 내 대각선 움직임입니다.");
        }
    }
}
