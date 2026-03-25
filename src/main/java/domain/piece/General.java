package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;

public class General implements StaticPositioned {

    private static final int INITIAL_FILE = 5;
    private static final int FAR_FROM_BASE_ROW = 1;

    private final Side side;

    public General(Side side) {
        this.side = side;
    }

    @Override
    public List<Intersection> initAt() {
        int baseRow = side.getBaseRow();
        int intialRow = side.getForwardedRow(baseRow, FAR_FROM_BASE_ROW);

        return List.of(new Intersection(intialRow, INITIAL_FILE));
    }
}
