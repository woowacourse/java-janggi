package domain.piece.strategy;

import domain.board.Position;
import domain.path.LinearPathGenerator;
import domain.path.PathInfo;
import domain.piece.BlockingPieceValidator;

import java.util.List;

public class ChariotMoveStrategy implements MoveStrategy {
    private final LinearPathGenerator pathGenerator;

    public ChariotMoveStrategy() {
        this.pathGenerator = new LinearPathGenerator();
    }

    @Override
    public List<Position> getPath(Position departure, Position destination) {
        return pathGenerator.getPath(departure, destination);
    }

    @Override
    public void validateBlockingPiece(List<PathInfo> pathInfos, Position destination) {
        BlockingPieceValidator.validateNoBlockingPiece(pathInfos, destination);
    }
}
