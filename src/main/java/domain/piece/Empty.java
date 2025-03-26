package domain.piece;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.piece.state.EmptyState;
import java.util.List;

public class Empty extends Piece {
    public Empty() {
        super(0, Side.EMPTY, null, new EmptyState());
    }

    @Override
    public List<Pattern> findMovablePath(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        throw new IllegalArgumentException("기물이 없어서 이동할 수 없습니다.");
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.EMPTY;
    }
}
