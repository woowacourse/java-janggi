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
        int preX = from.getX();
        int preY = from.getY();

        int nextX = to.getX();
        int nextY = to.getY();

        if (preX == nextX) {
            if (nextY > preY) {
                for (int y = preY + 1; y <= nextY; y++) {
                    path.add(new Position(preX, y));
                }
                return path;
            }
            for (int y = nextY; y < preY; y++) {
                path.add(new Position(preX, y));
            }
            return path;
        }

        if (nextX > preX) {
            for (int x = preX + 1; x <= nextX; x++) {
                path.add(new Position(x, preY));
            }
            return path;
        }
        for (int x = nextX; x <= preX; x++) {
            path.add(new Position(x, preY));
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
