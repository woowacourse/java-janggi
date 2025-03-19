package domain;

import domain.pattern.Pattern;
import java.util.List;

public class Empty extends Piece {
    public Empty() {
        super(0, null);
    }

    @Override
    public List<Pattern> findPath(Position beforePosition, Position afterPosition) {
        throw new IllegalStateException("움직일 말이 존재하지 않습니다.");
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
