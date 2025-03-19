package domain;

import domain.pattern.Pattern;
import domain.pattern.마Path;
import java.util.List;

public class 마 extends Piece {

    public 마(Side side) {
        super(5, side);
    }


    @Override
    public List<Pattern> findPath(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        return 마Path.getPath(beforeRow, beforeColumn, afterRow, afterColumn);
    }
}
