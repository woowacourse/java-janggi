package janggi.domain.piece;

import janggi.domain.mouveRule.MoveRule;
import janggi.domain.vo.position.Position;

import java.util.Objects;

public class EmptyPiece extends Piece {

    private static final EmptyPiece INSTANCE = new EmptyPiece();

    private EmptyPiece() {
        super(Team.NONE);
    }

    public static EmptyPiece getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.EMPTY;
    }

    @Override
    public MoveRule moveRule() {
        throw new UnsupportedOperationException("'빈 공간'은 이동할 수 없습니다.");
    }
}
