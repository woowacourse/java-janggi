package janggi.domain.strategy;

import janggi.domain.BoardInterface;
import janggi.domain.Position;
import janggi.domain.Side;

import java.util.List;

public class PoMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMovable(List<Position> path, Side side, BoardInterface boardInterface) {
        List<Position> piecesOnPath = path.stream()
                .filter(position -> !boardInterface.isEmpty(position))
                .toList();

        boolean chk = piecesOnPath.stream()
                .anyMatch(boardInterface::isPo);

        return piecesOnPath.size() == 1 && !chk;
    }
}
