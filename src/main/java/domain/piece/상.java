package domain.piece;

import domain.Position;
import domain.pattern.Pattern;
import domain.pattern.상Path;
import java.util.List;

public class 상 extends Piece {
    public 상(Side side) {
        super(3, side);
    }

    @Override
    public List<Pattern> findPath(Position beforePosition, Position afterPosition) {
        return 상Path.getPath(beforePosition, afterPosition);
    }
}
