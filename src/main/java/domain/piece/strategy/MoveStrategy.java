package domain.piece.strategy;

import domain.board.Position;
import domain.path.PathInfos;

import java.util.List;

public interface MoveStrategy {
    List<Position> getPath(Position departure, Position destination);

    void validateBlockingPiece(PathInfos pathInfos, Position destination);
}
