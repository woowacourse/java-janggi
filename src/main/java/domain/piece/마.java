package domain.piece;

import domain.Position;
import domain.pattern.Pattern;
import domain.pattern.마Path;
import java.util.List;

public class 마 extends Piece {

    public 마(Side side) {
        super(5, side);
    }


    @Override
    public List<Pattern> findPath(Position beforePosition, Position afterPosition) {
        return 마Path.getPath(beforePosition, afterPosition);
    }
}
