package domain.piece.strategy;

import domain.board.Palace;
import domain.board.Position;
import domain.path.PathInfos;

import java.util.List;

public class GeneralMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> getPath(Position departure, Position destination) {
        boolean isPalacePath = Palace.isPalacePath(departure, destination);
        validateMove(isPalacePath);

        return List.of(destination);
    }

    @Override
    public void validateBlockingPiece(PathInfos pathInfos, Position destination) {
        pathInfos.validateOnlyOneStep();
    }

    private void validateMove(boolean isInPalace) {
        if (!isInPalace) {
            throw new IllegalArgumentException("궁/사는 궁성 내에서만 이동 가능합니다.");
        }
    }
}
