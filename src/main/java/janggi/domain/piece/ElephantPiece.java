package janggi.domain.piece;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElephantPiece extends Piece {
    private static final List<List<Integer>> destinations = List.of(
            List.of(2, 3), List.of(3, 2), List.of(2, -3), List.of(3, -2),
            List.of(-2, 3), List.of(-3, 2), List.of(-2, -3), List.of(-3, -2)
    );

    public ElephantPiece(Team team) {
        super(team, Name.ELEPHANT);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int preX = from.getX();
        int preY = from.getY();

        int nextY = to.getY();
        int nextX = to.getX();

        for (List<Integer> destination : destinations) {
            if (nextY - preY == destination.get(1)
                    && nextX - preX == destination.get(0)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();

        int preX = from.getX();
        int preY = from.getY();

        int nextX = to.getX();
        int nextY = to.getY();

        if (Math.abs(preX - nextX) == 3) {
            if (nextX > preX) {
                path.add(new Position(preX + 1, preY));
                if (nextY > preY) {
                    path.add(new Position(preX + 2, preY + 1));
                    path.add(new Position(preX + 3, preY + 2));
                    return path;
                }
                path.add(new Position(preX + 2, preY - 1));
                path.add(new Position(preX + 3, preY - 2));
                return path;
            }
            path.add(new Position(preX - 1, preY));
            if (nextY > preY) {
                path.add(new Position(preX - 2, preY + 1));
                path.add(new Position(preX - 3, preY + 2));
                return path;
            }
            path.add(new Position(preX - 2, preY - 1));
            path.add(new Position(preX - 3, preY - 2));
            return path;
        }
        if (nextY > preY) {
            path.add(new Position(preX, preY + 1));
            if (nextX > preX) {
                path.add(new Position(preX + 1, preY + 2));
                path.add(new Position(preX + 2, preY + 3));
                return path;
            }
            path.add(new Position(preX - 1, preY + 2));
            path.add(new Position(preX - 2, preY + 3));
            return path;
        }
        path.add(new Position(preX, preY - 1));
        if (nextX > preX) {
            path.add(new Position(preX + 1, preY - 2));
            path.add(new Position(preX + 2, preY - 3));
            return path;
        }
        path.add(new Position(preX - 1, preY - 2));
        path.add(new Position(preX - 2, preY - 3));
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
