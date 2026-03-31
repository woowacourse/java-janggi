package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.board.BaseBoard;

public class Empty extends BasePiece {
    private static final String IMMOVABLE_PIECE_MESSAGE = "이동할 수 없는 말입니다.";

    public Empty() {
        super(Side.EMPTY, PieceType.NONE);
    }

    @Override
    public Route findRoute(Position start, Position end) {
        throw new IllegalStateException(IMMOVABLE_PIECE_MESSAGE);
    }

    @Override
    public void validateRoute(Route route, BaseBoard boardInfo) {
        throw new IllegalArgumentException(IMMOVABLE_PIECE_MESSAGE);
    }
}
