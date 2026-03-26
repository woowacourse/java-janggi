package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.BoardInterface;

import java.util.List;

public class None extends BasePiece {
    public None() {
        super(Side.EMPTY, PieceType.NONE);
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        return List.of();
    }

    @Override
    public boolean isMovable(List<Position> path, BoardInterface boardInterface) {
        return false;
    }

    @Override
    public boolean isPo() {
        return false;
    }
}