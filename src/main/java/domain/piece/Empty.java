package domain.piece;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import java.util.List;

public class Empty extends JanggiPiece {
    public Empty() {
        super(0, null, null);
    }

    @Override
    public List<Pattern> findPath(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        throw new IllegalStateException("움직일 말이 존재하지 않습니다.");
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
