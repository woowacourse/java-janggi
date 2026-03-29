package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.MoveStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChariotPiece extends Piece {
    public ChariotPiece(Team team, MoveStrategy moveStrategy) {
        super(team, Name.CHARIOT, moveStrategy);
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
