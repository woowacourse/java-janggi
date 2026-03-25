package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;

public class Cannon extends Piece implements StaticPositioned {

    private static final int FAR_FROM_BASE_ROW = 2;
    private static final List<Integer> INITAL_FILES = List.of(2, 8);

    public Cannon(Side side) {
        super(side);
    }

    @Override
    public List<Intersection> initAt() {
        int baseRow = side.getBaseRow();
        int initialRow = side.getForwardedRow(baseRow, FAR_FROM_BASE_ROW);

        return INITAL_FILES.stream()
                .map(file -> new Intersection(initialRow, file))
                .toList();
    }

    @Override
    public String toString() {
        return "Cannon{" +
                "side=" + side +
                '}';
    }
}
