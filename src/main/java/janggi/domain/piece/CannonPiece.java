package janggi.domain.piece;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CannonPiece extends Piece {
    public CannonPiece(Team team) {
        super(team, Name.CANNON);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int preX = from.getX();
        int preY = from.getY();

        int nextX = to.getX();
        int nextY = to.getY();

        return (preX == nextX && preY != nextY) || (preX != nextX && preY == nextY);
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
    public boolean determineMovingRule(Map<Position, Piece> positionPieces, Position to) {
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
