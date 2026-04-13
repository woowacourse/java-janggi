package domain.piece.strategy;

import domain.board.Position;
import domain.path.LinearPathGenerator;
import domain.path.PathInfos;

import java.util.List;

public class CannonMoveStrategy implements MoveStrategy {
    private final LinearPathGenerator pathGenerator;

    public CannonMoveStrategy() {
        this.pathGenerator = new LinearPathGenerator();
    }

    @Override
    public List<Position> getPath(Position departure, Position destination) {
        return pathGenerator.getPath(departure, destination);
    }

    @Override
    public void validateBlockingPiece(PathInfos pathInfos, Position destination) {
        pathInfos.validateHasBlockingPiece(destination);
    }
}
