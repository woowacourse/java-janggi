package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.MoveStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CannonPiece extends Piece {
    public CannonPiece(Team team, MoveStrategy moveStrategy) {
        super(team, Name.CANNON, moveStrategy);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();

        int currentX = from.getX();
        int currentY = from.getY();

        int stepX = Integer.compare(to.getX(), from.getX());
        int stepY = Integer.compare(to.getY(), from.getY());

        while (currentX != to.getX() || currentY != to.getY()) {
            currentX += stepX;
            currentY += stepY;
            path.add(new Position(currentX, currentY));
        }
        return path;
    }

    @Override
    public boolean canMoveBySpecialMovingRule(Map<Position, Piece> positionPieces, Position to) {
        if (positionPieces.size() >= 3) {
            return false;
        }
        long count = positionPieces.values().stream()
                .filter(piece -> getPieceName().equals(piece.getPieceName()))
                .count();
        if (count != 0) {
            return false;
        }

        if (positionPieces.size() == 2) {
            if (positionPieces.containsKey(to)) {
                Piece piece = positionPieces.get(to);
                return !piece.isSameTeam(this);
            }
            return false;
        }

        return !positionPieces.containsKey(to);
    }
}
