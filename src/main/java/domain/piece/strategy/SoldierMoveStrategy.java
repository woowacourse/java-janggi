package domain.piece.strategy;

import domain.board.Palace;
import domain.board.Position;
import domain.path.PathInfo;
import domain.path.Direction;
import domain.path.SingleStepLinearPathGenerator;
import domain.piece.BlockingPieceValidator;

import java.util.ArrayList;
import java.util.List;

public class SoldierMoveStrategy implements MoveStrategy {
    private final Direction forwardDirection;

    public SoldierMoveStrategy(Direction forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

    @Override
    public List<Position> getPath(Position departure, Position destination) {
        boolean isPalacePath = Palace.isPalacePath(departure, destination);
        Direction direction = SingleStepLinearPathGenerator.decideSingleLinearDirection(departure, destination, isPalacePath);

        validateMove(direction, isPalacePath);
        return List.of(destination);
    }

    @Override
    public void validateBlockingPiece(List<PathInfo> pathInfos, Position destination) {
        BlockingPieceValidator.validateOnlyOneStep(pathInfos);
    }

    private void validateMove(Direction direction, boolean isInPalace) {
        List<Direction> allowedDirections = new ArrayList<>(List.of(Direction.LEFT, Direction.RIGHT, forwardDirection));
        if (isInPalace && direction.getDeltaY() == forwardDirection.getDeltaY()) {
            allowedDirections.add(direction);
        }

        if (!allowedDirections.contains(direction)) {
            throw new IllegalArgumentException("졸/병은 후퇴할 수 없습니다.");
        }
    }
}
