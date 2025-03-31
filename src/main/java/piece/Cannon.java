package piece;

import game.Team;
import location.PathUtility;
import location.Position;
import java.util.List;

public class Cannon extends Piece {
    private Position currentPosition;

    public Cannon(int pieceId, Team team, Position currentPosition) {
        super(pieceId, team, PieceType.CANNON);
        this.currentPosition = currentPosition;
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
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
    public void updateCurrentPosition(Position destination) {
        currentPosition = destination;
    }

    @Override
    public boolean isPlacedAt(Position targetPosition) {
        return currentPosition.equals(targetPosition);
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
