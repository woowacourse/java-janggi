package janggi.domain.piece;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChariotPiece extends Piece {
    public ChariotPiece(Team team) {
        super(team, Name.CHARIOT);
    }

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        return (from.isSameX(to) && !from.isSameY(to))
                || (!from.isSameX(to) && from.isSameY(to));
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        int stepX = Integer.compare(to.x(), from.x());
        int stepY = Integer.compare(to.y(), from.y());
        Position currentPosition = from;

        while (!currentPosition.equals(to)) {
            currentPosition = currentPosition.moveBy(stepX, stepY);
            path.add(currentPosition);
        }
        return path;
    }

    @Override
    public boolean canMoveBySpecialMovingRule(Map<Position, Piece> positionPieces, Position to) {
        if (positionPieces.size() >= 2) {
            return false;
        }
        for (Position position : positionPieces.keySet()) {
            if (position.equals(to)) {
                return !isSameTeam(positionPieces.get(position));
            }
            return false;
        }
        return true;
    }
}
