package domain.board;

import java.util.Map;
import domain.pieces.Ma;
import domain.pieces.Piece;
import domain.pieces.Sang;
import domain.pieces.Side;
import domain.position.Position;

public class RightSangSetup extends GeneralSangSetup {

    @Override
    protected Map<Position, Piece> getSangAndMaPositions(Side side) {
        if (side.isCho()) {
            return Map.of(
                    new Position(0, 1), new Ma(Side.CHO),
                    new Position(0, 2), new Sang(Side.CHO),
                    new Position(0, 6), new Ma(Side.CHO),
                    new Position(0, 7), new Sang(Side.CHO)
            );
        }
        return Map.of(
                new Position(9, 1), new Sang(Side.HAN),
                new Position(9, 2), new Ma(Side.HAN),
                new Position(9, 6), new Sang(Side.HAN),
                new Position(9, 7), new Ma(Side.HAN)
        );
    }
}
