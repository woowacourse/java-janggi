package domain.strategy;

import domain.Piece;
import domain.Type;
import domain.vo.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CannonMoveStrategy implements MoveStrategy {

    private static final int CANNON_REQUIRED_PIECE_COUNT = 1;

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

        Map<Position, Piece> intermediate = new HashMap<>(piecesOnPath);
        Piece target = intermediate.remove(to);

        if (intermediate.values().stream().anyMatch(piece -> piece.getType() == Type.CANNON)) {
            return false;
        }

        if (intermediate.size() != CANNON_REQUIRED_PIECE_COUNT) {
            return false;
        }

        if (target != null && target.getType() == Type.CANNON) {
            return false;
        }

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