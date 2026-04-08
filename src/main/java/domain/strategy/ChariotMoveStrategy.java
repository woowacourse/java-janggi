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
        if (isInPalaceAndCanDiagonalMove(from)) {
            if (isDiagonal(from, to)) {
                if (Math.abs(from.getRow() - to.getRow()) + Math.abs(from.getCol() - to.getCol()) <= 4) {
                    return false;
                }
            }
        }

        return from.getCol() != to.getCol() && from.getRow() != to.getRow();
    }

    private boolean isInPalaceAndCanDiagonalMove(final Position position) {
        int row = position.getRow();
        int column = position.getCol();
        if ((row == 0 && column == 4) || (row == 2 && column == 4) || (row == 1 && column == 3) || (row == 1 && column == 5)
        || (row == 7 && column == 4) || (row == 9 && column == 4) || (row == 8 && column == 3) || (row == 8 && column == 5)) {
            return false;
        }

        return true;
    }

    private boolean isDiagonal(final Position from, final Position to) {
        return Math.abs(from.getRow() - to.getRow()) >= 1 && Math.abs(from.getCol() - to.getCol()) >= 1;
    }
}