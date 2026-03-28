package board;

import java.util.Map;
import pieces.Ma;
import pieces.Piece;
import pieces.Sang;
import pieces.Side;
import position.Position;

public class RightSangSetup extends GeneralSangSetup {

    @Override
    protected Map<Position, Piece> getSangAndMaPositions(Side side) {
        Map<Position, Piece> sangMaPositions;
        if (side.isCho()) {
            sangMaPositions = Map.of(
                new Position(0, 1), new Ma(Side.CHO),
                new Position(0, 2), new Sang(Side.CHO),
                new Position(0, 6), new Ma(Side.CHO),
                new Position(0, 7), new Sang(Side.CHO)
            );
        } else {
            sangMaPositions = Map.of(
                new Position(9, 1), new Sang(Side.HAN),
                new Position(9, 2), new Ma(Side.HAN),
                new Position(9, 6), new Sang(Side.HAN),
                new Position(9, 7), new Ma(Side.HAN)
            );
        }
        return Map.copyOf(sangMaPositions);
    }
}
