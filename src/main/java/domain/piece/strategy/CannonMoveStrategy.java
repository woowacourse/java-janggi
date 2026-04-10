package domain.piece.strategy;

import domain.board.Position;
import domain.path.LinearPathGenerator;
import domain.path.PathInfo;
import domain.piece.BlockingPieceValidator;

import java.util.List;

public class CannonMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> getPath(Position departure, Position destination) {
        return LinearPathGenerator.getPath(departure, destination);
    }

    @Override
    public void validateBlockingPiece(List<PathInfo> pathInfos, Position destination) {
        BlockingPieceValidator.validateHasBlockingPiece(pathInfos);
    }
}
