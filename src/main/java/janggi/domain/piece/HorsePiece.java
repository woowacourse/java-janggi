package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.MoveStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HorsePiece extends Piece {
    private static final List<List<Integer>> destinations = List.of(
            List.of(1, 2), List.of(2, 1), List.of(1, -2), List.of(2, -1),
            List.of(-1, 2), List.of(-2, 1), List.of(-1, -2), List.of(-2, -1)
    );

    public HorsePiece(Team team, MoveStrategy moveStrategy) {
        super(team, Name.HORSE, moveStrategy);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();

        int preX = from.getX();
        int preY = from.getY();

        int nextX = to.getX();
        int nextY = to.getY();

        if (Math.abs(preX - nextX) == 2) {
            if (nextX > preX) {
                path.add(new Position(preX + 1, preY));
                if (nextY > preY) {
                    path.add(new Position(preX + 2, preY + 1));
                    return path;
                }
                path.add(new Position(preX + 2, preY - 1));
                return path;
            }
            path.add(new Position(preX - 1, preY));
            if (nextY > preY) {
                path.add(new Position(preX - 2, preY + 1));
                return path;
            }
            path.add(new Position(preX - 2, preY - 1));
            return path;
        }
        if (nextY > preY) {
            path.add(new Position(preX, preY + 1));
            if (nextX > preX) {
                path.add(new Position(preX + 1, preY + 2));
                return path;
            }
            path.add(new Position(preX - 1, preY + 2));
            return path;
        }
        path.add(new Position(preX, preY - 1));
        if (nextX > preX) {
            path.add(new Position(preX + 1, preY - 2));
            return path;
        }
        path.add(new Position(preX - 1, preY - 2));
        return path;
    }

    @Override
    public boolean determineMovingRule(Map<Position, Piece> positionPieces, Position to) {
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
