package domain;

import domain.pattern.Pattern;
import domain.pattern.병Path;
import domain.pattern.졸Path;
import java.util.List;

public class 졸병 extends Piece {

    public 졸병(Side side) {
        super(2, side);
    }

    @Override
    public List<Pattern> findPath(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        if (side == Side.초) {
            return 졸Path.getPath(beforeRow, beforeColumn, afterRow, afterColumn);
        }
        return 병Path.getPath(beforeRow, beforeColumn, afterRow, afterColumn);
    }
}
