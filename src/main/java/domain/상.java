package domain;

import domain.pattern.Pattern;
import domain.pattern.상Path;
import java.util.List;

public class 상 extends Piece {
    public 상(Side side) {
        super(3, side);
    }

    @Override
    public List<Pattern> findPath(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        return 상Path.getPath(beforeRow, beforeColumn, afterRow, afterColumn);
    }
}
