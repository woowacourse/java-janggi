package pieceProperty;

import static pieceProperty.PieceType.BYEONG;
import static pieceProperty.PieceType.CHA;
import static pieceProperty.PieceType.CHO_JANGGUN;
import static pieceProperty.PieceType.CHO_SA;
import static pieceProperty.PieceType.HAN_JANGGUN;
import static pieceProperty.PieceType.HAN_SA;
import static pieceProperty.PieceType.JOL;
import static pieceProperty.PieceType.MA;
import static pieceProperty.PieceType.PO;
import static pieceProperty.PieceType.SANG;

import java.util.HashMap;
import java.util.Map;
import player.JanggiPan;

public class JanggiPieceInitializer {

    public JanggiPan hanInit() {
        Map<Position, PieceType> janggiPan = new HashMap<>();

        janggiPan.put(new Position(0, 0), CHA);
        janggiPan.put(new Position(0, 8), CHA);

        janggiPan.put(new Position(0, 1), SANG);
        janggiPan.put(new Position(0, 7), SANG);

        janggiPan.put(new Position(0, 2), MA);
        janggiPan.put(new Position(0, 6), MA);

        janggiPan.put(new Position(0, 3), HAN_SA);
        janggiPan.put(new Position(0, 5), HAN_SA);

        janggiPan.put(new Position(1, 4), HAN_JANGGUN);

        janggiPan.put(new Position(2, 1), PO);
        janggiPan.put(new Position(2, 7), PO);

        janggiPan.put(new Position(3, 0), JOL);
        janggiPan.put(new Position(3, 2), JOL);
        janggiPan.put(new Position(3, 4), JOL);
        janggiPan.put(new Position(3, 6), JOL);
        janggiPan.put(new Position(3, 8), JOL);

        return new JanggiPan(janggiPan);
    }

    public JanggiPan choInit() {

        Map<Position, PieceType> janggiPan = new HashMap<>();

        janggiPan.put(new Position(9, 8), CHA);
        janggiPan.put(new Position(9, 0), CHA);

        janggiPan.put(new Position(9, 1), SANG);
        janggiPan.put(new Position(9, 7), SANG);

        janggiPan.put(new Position(9, 2), MA);
        janggiPan.put(new Position(9, 6), MA);

        janggiPan.put(new Position(9, 3), CHO_SA);
        janggiPan.put(new Position(9, 5), CHO_SA);

        janggiPan.put(new Position(8, 4), CHO_JANGGUN);

        janggiPan.put(new Position(7, 1), PO);
        janggiPan.put(new Position(7, 7), PO);

        janggiPan.put(new Position(6, 2), BYEONG);
        janggiPan.put(new Position(6, 4), BYEONG);
        janggiPan.put(new Position(6, 0), BYEONG);
        janggiPan.put(new Position(6, 6), BYEONG);
        janggiPan.put(new Position(6, 8), BYEONG);

        return new JanggiPan(janggiPan);
    }
}
