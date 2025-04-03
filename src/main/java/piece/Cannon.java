package piece;

import game.Team;
import location.PathManager;
import location.Position;
import java.util.List;

public class Cannon extends Piece {
    private final PathManager pathManager;
    private Position currentPosition;

    public Cannon(int id, Team team, PathManager pathManager, Position currentPosition) {
        super(id, team);
        this.currentPosition = currentPosition;
        this.pathManager = pathManager;
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CANNON;
    }

    @Override
    public int getScore() {
        return 7;
    }

    @Override
    public void validateDestination(Position destination) {
        pathManager.checkStraightMovement(currentPosition, destination);
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
        List<Position> paths = pathManager.calculateOneDirectionPaths(currentPosition, destination);
        return (int) paths.stream()
                .filter(pieces::isContainedPieceAtPosition)
                .map(pieces::getByPosition)
                .filter(PieceType::isNotCannon)
                .count();
    }

    private int calculateCannonCountInPaths(Pieces pieces, Position destination) {
        List<Position> paths = pathManager.calculateOneDirectionPaths(currentPosition, destination);
        return (int) paths.stream()
                .filter(pieces::isContainedPieceAtPosition)
                .map(pieces::getByPosition)
                .filter(PieceType::isCannon)
                .count();
    }
}
