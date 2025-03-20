package domain.piece;

import domain.Position;
import domain.pattern.Pattern;
import domain.pattern.궁Path;
import java.util.List;

public class 궁 extends Piece {


    public 궁(Side side) {
        super(0, side);
    }

    @Override
    public List<Pattern> findPath(Position beforePosition, Position afterPosition) {
        return 궁Path.getPath(beforePosition, afterPosition);
    }
}
