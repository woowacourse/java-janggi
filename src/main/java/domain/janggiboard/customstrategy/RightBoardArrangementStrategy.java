package domain.janggiboard.customstrategy;

import domain.piece.JanggiPiece;
import domain.piece.JanggiPieceType;
import domain.piece.JanggiSide;
import domain.position.JanggiPosition;
import java.util.HashMap;
import java.util.Map;

public class RightBoardArrangementStrategy implements BoardArrangementStrategy {

    private final JanggiSide side;

    public RightBoardArrangementStrategy(JanggiSide side) {
        this.side = side;
    }

    @Override
    public Map<JanggiPosition, JanggiPiece> setUp() {
        final Map<JanggiPosition, JanggiPiece> army = new HashMap<>();
        if (side == JanggiSide.CHO) {
            army.put(new JanggiPosition(0, 2), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.HORSE));
            army.put(new JanggiPosition(0, 3), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.ELEPHANT));
            army.put(new JanggiPosition(0, 7), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.HORSE));
            army.put(new JanggiPosition(0, 8), new JanggiPiece(JanggiSide.CHO, JanggiPieceType.ELEPHANT));
        }
        if (side == JanggiSide.HAN) {
            army.put(new JanggiPosition(1, 2), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.HORSE));
            army.put(new JanggiPosition(1, 3), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.ELEPHANT));
            army.put(new JanggiPosition(1, 7), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.HORSE));
            army.put(new JanggiPosition(1, 8), new JanggiPiece(JanggiSide.HAN, JanggiPieceType.ELEPHANT));
        }
        return army;
    }
}
