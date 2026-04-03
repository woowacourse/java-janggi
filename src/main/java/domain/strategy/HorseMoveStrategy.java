package domain.strategy;

import domain.Piece;
import domain.vo.Position;

import java.util.List;
import java.util.Map;

public class HorseMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> getPath(final Position from, final Position to) {
        if (isNotCorrectPath(from, to)) {
            return List.of();
        }

        int nx = to.getRow() - from.getRow();
        int ny = to.getCol() - from.getCol();
        Position mid = Position.of(from.getRow() + nx / 2, from.getCol() + ny / 2);
        return List.of(mid, to);
    }

    @Override
    public boolean canMove(final Piece mover, final Position from, final Position to, final Map<Position, Piece> piecesOnPath) {
        if (isNotCorrectPath(from, to)) {
            return false;
        }

        int nx = to.getRow() - from.getRow();
        int ny = to.getCol() - from.getCol();
        Position mid = Position.of(from.getRow() + nx / 2, from.getCol() + ny / 2);

        if (piecesOnPath.containsKey(mid)) {
            return false;
        }

        Piece target = piecesOnPath.get(to);
        if (target == null) {
            return true;
        }
        return mover.isAnotherTeam(target);
    }

    private boolean isNotCorrectPath(final Position from, final Position to) {
        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int colDiff = Math.abs(from.getCol() - to.getCol());

        return !((rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1));
    }
}