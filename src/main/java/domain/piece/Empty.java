package domain.piece;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import java.util.List;

public class Empty extends Piece {
    public Empty() {
        super(0, Side.EMPTY, null, null);
    }

    @Override
    public List<Pattern> findMovablePath(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        throw new IllegalArgumentException("움직일 말이 존재하지 않습니다.");
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
