package domain.piece;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.route.limited_route.EmptyRoute;
import java.util.List;

public final class Empty extends JanggiPiece {
    public Empty() {
        super(0, JanggiSide.NONE, new EmptyRoute());
    }

    @Override
    public List<Pattern> findPath(final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        throw new IllegalStateException("움직일 말이 존재하지 않습니다.");
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
