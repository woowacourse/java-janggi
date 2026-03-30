package janggi.domain.policy;

import janggi.domain.board.BoardInterface;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class JumpPolicy implements RoutePolicy {
    @Override
    public boolean isMovable(List<Position> path, Side side, BoardInterface boardInterface) {
        List<Position> innerPath = new ArrayList<>(path.subList(1, path.size()));
        boolean hasPo = innerPath.stream()
                .anyMatch(position -> boardInterface.isEqualPieceType(position, PieceType.PO));
        if (hasPo) {
            return false;
        }
        Position target = innerPath.removeLast();

        return isMovableFirst(innerPath, boardInterface) && isMovableLast(target, side, boardInterface);
    }

    private boolean isMovableFirst(List<Position> path, BoardInterface boardInterface) {
        List<Position> piecesOnPath = path.stream()
                .filter(position -> !boardInterface.isEmpty(position))
                .toList();

        return piecesOnPath.size() == 1;
    }

    private boolean isMovableLast(Position position, Side side, BoardInterface boardInterface) {
        return !boardInterface.isAlly(side, position);
    }
}
