package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.BaseBoard;

import java.util.List;

public class None extends BasePiece {
    private static final String IMMOVABLE_PIECE_MESSAGE = "이동할 수 없는 말입니다.";

    public None() {
        super(Side.EMPTY, PieceType.NONE);
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        return List.of();
    }

    @Override
    public void validateRoute(List<Position> path, BaseBoard baseBoard) {
        throw new IllegalArgumentException(IMMOVABLE_PIECE_MESSAGE);
    }
}