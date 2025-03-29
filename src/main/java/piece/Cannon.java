package piece;

import location.PathUtility;
import location.Position;
import java.util.List;
import store.Pieces;

public class Cannon implements Piece {
    private final PieceType pieceType;
    private final Position currentPosition;

    public Cannon(Position currentPosition) {
        this.pieceType = PieceType.CANNON;
        this.currentPosition = currentPosition;
    }

    @Override
    public void validateDestination(Position destination) {
        PathUtility.checkStraightMovement(currentPosition, destination);
    }

    @Override
    public void validatePaths(Pieces pieces, Position destination) {
        int notCannonCount = calculateNotCannonCountInPaths(pieces, destination);
        int cannonCount = calculateCannonCountInPaths(pieces, destination);

        if (notCannonCount != 1 || cannonCount > 0) {
            throw new IllegalArgumentException("[ERROR] 반드시 포가 아닌 기물 하나를 넘어야 합니다.");
        }
    }

    @Override
    public Piece move(Position destination) {
        return new Cannon(destination);
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

    private int calculateNotCannonCountInPaths(Pieces pieces, Position destination) {
        List<Position> paths = PathUtility.calculateOneDirectionPaths(currentPosition, destination);
        return (int) paths.stream()
                .filter(pieces::isContainedPieceAtPosition)
                .map(pieces::getByPosition)
                .filter(PieceType::isNotCannon)
                .count();
    }

    private int calculateCannonCountInPaths(Pieces pieces, Position destination) {
        List<Position> paths = PathUtility.calculateOneDirectionPaths(currentPosition, destination);
        return (int) paths.stream()
                .filter(pieces::isContainedPieceAtPosition)
                .map(pieces::getByPosition)
                .filter(PieceType::isCannon)
                .count();
    }
}
