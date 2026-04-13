package domain.strategy;

import domain.Piece;
import domain.vo.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PalaceMoveStrategy implements MoveStrategy {

    private final MoveStrategy base;

    public PalaceMoveStrategy(MoveStrategy base) {
        this.base = base;
    }

    @Override
    public List<Position> getPath(Position from, Position to) {
        if (Palace.canDiagonalInPalace(from, to)) {
            return buildDiagonalPath(from, to);
        }
        return base.getPath(from, to);
    }

    @Override
    public boolean canMove(Piece mover, Position from, Position to,
                           Map<Position, Piece> piecesOnPath) {
        return base.canMove(mover, from, to, piecesOnPath);
    }

    private List<Position> buildDiagonalPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        int dr = Integer.compare(to.getRow(), from.getRow());
        int dc = Integer.compare(to.getCol(), from.getCol());
        int r = from.getRow() + dr;
        int c = from.getCol() + dc;
        while (r != to.getRow() || c != to.getCol()) {
            path.add(Position.of(r, c));
            r += dr;
            c += dc;
        }
        path.add(to);
        return path;
    }
}
