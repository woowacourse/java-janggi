package janggi.domain.policy;

import janggi.domain.board.BaseBoard;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class JumpPolicy implements RoutePolicy {
    @Override
    public boolean isMovable(List<Position> path, Side side, BaseBoard baseBoard) {
        List<Position> innerPath = new ArrayList<>(path.subList(1, path.size()));
        boolean hasPo = innerPath.stream()
                .anyMatch(position -> baseBoard.isEqualPieceType(position, PieceType.PO));
        if (hasPo) {
            return false;
        }
        Position target = innerPath.removeLast();

        return isMovableFirst(innerPath, baseBoard) && isMovableLast(target, side, baseBoard);
    }

    private boolean isMovableFirst(List<Position> path, BaseBoard baseBoard) {
        List<Position> piecesOnPath = path.stream()
                .filter(position -> !baseBoard.isEmpty(position))
                .toList();

        return piecesOnPath.size() == 1;
    }

    private boolean isMovableLast(Position position, Side side, BaseBoard baseBoard) {
        return !baseBoard.isAlly(side, position);
    }
}
