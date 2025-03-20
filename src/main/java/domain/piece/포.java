package domain.piece;

import domain.Position;
import domain.pattern.Pattern;
import domain.pattern.포Path;
import java.util.List;

public class 포 extends Piece {
    public 포(Side side) {
        super(7, side);
    }

    @Override
    public List<Pattern> findPath(Position beforePosition, Position afterPosition) {
        return 포Path.getPath(beforePosition, afterPosition);
    }

}
