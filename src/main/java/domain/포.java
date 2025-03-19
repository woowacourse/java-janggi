package domain;

import domain.pattern.Pattern;
import domain.pattern.포Path;
import java.util.List;

public class 포 extends Piece {
    public 포(Side side) {
        super(7, side);
    }

    @Override
    public List<Pattern> findPath(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        return 포Path.getPath(beforeRow, beforeColumn, afterRow, afterColumn);
    }

}
