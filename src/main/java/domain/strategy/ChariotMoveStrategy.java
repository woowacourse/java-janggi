package domain.strategy;

import domain.Piece;
import domain.vo.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChariotMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> getPath(final Position from, final Position to) {
        if (isNotCorrectPath(from, to)) {
            return List.of();
        }

        List<Position> path = new ArrayList<>();
        int nx = Integer.compare(to.getRow(), from.getRow());
        int ny = Integer.compare(to.getCol(), from.getCol());

        int row = from.getRow() + nx;
        int col = from.getCol() + ny;
        while (row != to.getRow() || col != to.getCol()) {
            path.add(Position.of(row, col));
            row += nx;
            col += ny;
        }
        path.add(to);
        return path;
    }

    @Override
    public boolean canMove(final Piece mover, final Position from, final Position to, final Map<Position, Piece> piecesOnPath) {
        if (isNotCorrectPath(from, to)) {
            return false;
        }

        for (Position pos : piecesOnPath.keySet()) {
            if (!pos.equals(to)) {
                return false;
            }
        }

        Piece target = piecesOnPath.get(to);
        if (target == null) {
            return true;
        }
        return mover.isAnotherTeam(target);
    }

    private boolean isNotCorrectPath(final Position from, final Position to) {
        if (Palace.canDiagonalInPalace(from, to)) {
            return false;
        }
        return from.getCol() != to.getCol() && from.getRow() != to.getRow();
    }
}