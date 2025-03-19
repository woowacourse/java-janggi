package domain;

import domain.pattern.Pattern;
import domain.pattern.궁Path;
import java.util.List;

public class 궁 extends Piece {


    public 궁(Side side) {
        super(0, side);
    }

    @Override
    public List<Pattern> findPath(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        return 궁Path.getPath(beforeRow, beforeColumn, afterRow, afterColumn);
    }
}
