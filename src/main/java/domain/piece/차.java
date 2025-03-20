package domain.piece;

import domain.Position;
import domain.pattern.Pattern;
import domain.pattern.차Path;
import java.util.List;

public class 차 extends Piece {
    public 차(Side side) {
        super(13, side);
    }

    @Override
    public List<Pattern> findPath(Position beforePosition, Position afterPosition) {
        return 차Path.getPath(beforePosition, afterPosition);
    }
}
