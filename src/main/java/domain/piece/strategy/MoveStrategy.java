package domain.piece.strategy;

import domain.path.PathInfo;
import domain.board.Position;

import java.util.List;

public interface MoveStrategy {
    List<Position> getPath(Position departure, Position destination);
    void validateBlockingPiece(List<PathInfo> pathInfos, Position destination);
}
