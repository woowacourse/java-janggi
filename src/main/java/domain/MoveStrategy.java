package domain;

import java.util.List;

public interface MoveStrategy {
    List<Position> getPath(Position departure, Position destination);
    void validateBlockingPiece(List<PathInfo> pathInfos, Position departure, Position destination);
}
