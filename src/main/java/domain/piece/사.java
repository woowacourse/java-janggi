package domain.piece;

import domain.Position;
import domain.pattern.Pattern;
import domain.pattern.사Path;
import java.util.List;

public class 사 extends Piece {
    public 사(Side side) {
        super(3, side);
    }

    @Override
    public List<Pattern> findPath(Position beforePosition, Position afterPosition) {
        return 사Path.getPath(beforePosition, afterPosition);
    }
}
