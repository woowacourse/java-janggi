package domain.piece.strategy;

import domain.board.Position;
import domain.path.JumpPathGenerator;
import domain.path.PathInfo;
import domain.piece.BlockingPieceValidator;

import java.util.List;

public class HorseMoveStrategy implements MoveStrategy {
    private final JumpPathGenerator pathGenerator;

    public HorseMoveStrategy() {
        this.pathGenerator = new JumpPathGenerator();
    }

    @Override
    public List<Position> getPath(Position departure, Position destination) {
        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);

        validateMove(deltaX, deltaY);

        return pathGenerator.getPath(departure, destination, 1);
    }

    @Override
    public void validateBlockingPiece(List<PathInfo> pathInfos, Position destination) {
        BlockingPieceValidator.validateNoBlockingPiece(pathInfos, destination);
    }

    private void validateMove(int deltaX, int deltaY) {
        if (!isHorseMove(deltaX, deltaY)) {
            throw new IllegalArgumentException("상은 직진 후, 대각선 방향으로 두 칸 이동 가능합니다.");
        }
    }

    private boolean isHorseMove(int deltaX, int deltaY) {
        int absoluteX = Math.abs(deltaX);
        int absoluteY = Math.abs(deltaY);

        return (absoluteX == 1 && absoluteY == 2) || (absoluteX == 2 && absoluteY == 1);
    }
}
