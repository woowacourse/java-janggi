package domain.piece.strategy;

import domain.board.Position;
import domain.path.JumpPathGenerator;
import domain.path.PathInfo;
import domain.path.PathInfos;

import java.util.List;

public class ElephantMoveStrategy implements MoveStrategy {
    private final JumpPathGenerator pathGenerator;

    public ElephantMoveStrategy() {
        this.pathGenerator = new JumpPathGenerator();
    }

    @Override
    public List<Position> getPath(Position departure, Position destination) {
        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);

        validateMove(deltaX, deltaY);

        return pathGenerator.getPath(departure, destination, 2);
    }

    @Override
    public void validateBlockingPiece(PathInfos pathInfos, Position destination) {
        pathInfos.validateNoBlockingPiece(destination);
    }

    private void validateMove(int deltaX, int deltaY) {
        if (!isElephantMove(deltaX, deltaY)) {
            throw new IllegalArgumentException("마는 직진 후, 대각선 방향으로 한 칸 이동 가능합니다.");
        }
    }

    private boolean isElephantMove(int deltaX, int deltaY) {
        int absoluteX = Math.abs(deltaX);
        int absoluteY = Math.abs(deltaY);

        return (absoluteX == 2 && absoluteY == 3) || (absoluteX == 3 && absoluteY == 2);
    }
}
