package board;

import java.util.HashMap;
import java.util.Map;
import pieces.Ma;
import pieces.Piece;
import pieces.Sang;
import pieces.Side;
import position.Position;

public class LeftSangSetup extends GeneralSangSetup {

    @Override
    protected Map<Position, Piece> getSangAndMaPositions(Side side) {
        Map<Position, Piece> positions = new HashMap<>();
        positions.put(toPosition(side, 0, 1), new Sang(side));
        positions.put(toPosition(side, 0, 2), new Ma(side));
        positions.put(toPosition(side, 0, 6), new Sang(side));
        positions.put(toPosition(side, 0, 7), new Ma(side));
        return Map.copyOf(positions);
    }
}
