package domain;

import domain.pattern.Pattern;
import domain.pattern.차Path;
import java.util.List;

public class 차 extends Piece {
    public 차(Side side) {
        super(13, side);
    }

    @Override
    public List<Pattern> findPath(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        return 차Path.getPath(beforeRow, beforeColumn, afterRow, afterColumn);
    }
}
