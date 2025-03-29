package pieceProperty;

import java.util.HashMap;
import java.util.Map;
import movementRule.Byeong;
import movementRule.Jol;
import movementRule.Ma;
import movementRule.Sang;
import movementRule.linearMover.Cha;
import movementRule.linearMover.Po;
import movementRule.omniDirectionMover.Janggun;
import movementRule.omniDirectionMover.Sa;
import piece.Piece;
import player.JanggiPan;

public class JanggiPieceInitializer {

    public JanggiPan hanInit() {
        Map<Position, Piece> janggiPan = new HashMap<>();

        janggiPan.put(new Position(0, 0), new Piece(new Cha()));
        janggiPan.put(new Position(0, 8), new Piece(new Cha()));

        janggiPan.put(new Position(0, 1), new Piece(new Sang()));
        janggiPan.put(new Position(0, 7), new Piece(new Sang()));

        janggiPan.put(new Position(0, 2), new Piece(new Ma()));
        janggiPan.put(new Position(0, 6), new Piece(new Ma()));

        janggiPan.put(new Position(0, 3), new Piece(new Sa()));
        janggiPan.put(new Position(0, 5), new Piece(new Sa()));

        janggiPan.put(new Position(1, 4), new Piece(new Janggun()));

        janggiPan.put(new Position(2, 1), new Piece(new Po()));
        janggiPan.put(new Position(2, 7), new Piece(new Po()));

        janggiPan.put(new Position(3, 0), new Piece(new Jol()));
        janggiPan.put(new Position(3, 2), new Piece(new Jol()));
        janggiPan.put(new Position(3, 4), new Piece(new Jol()));
        janggiPan.put(new Position(3, 6), new Piece(new Jol()));
        janggiPan.put(new Position(3, 8), new Piece(new Jol()));

        return new JanggiPan(janggiPan);
    }

    public JanggiPan choInit() {

        Map<Position, Piece> janggiPan = new HashMap<>();

        janggiPan.put(new Position(9, 0), new Piece(new Cha()));
        janggiPan.put(new Position(9, 8), new Piece(new Cha()));

        janggiPan.put(new Position(9, 1), new Piece(new Sang()));
        janggiPan.put(new Position(9, 7), new Piece(new Sang()));

        janggiPan.put(new Position(9, 2), new Piece(new Ma()));
        janggiPan.put(new Position(9, 6), new Piece(new Ma()));

        janggiPan.put(new Position(9, 3), new Piece(new Sa()));
        janggiPan.put(new Position(9, 5), new Piece(new Sa()));

        janggiPan.put(new Position(8, 4), new Piece(new Janggun()));

        janggiPan.put(new Position(7, 1), new Piece(new Po()));
        janggiPan.put(new Position(7, 7), new Piece(new Po()));

        janggiPan.put(new Position(6, 0), new Piece(new Byeong()));
        janggiPan.put(new Position(6, 2), new Piece(new Byeong()));
        janggiPan.put(new Position(6, 4), new Piece(new Byeong()));
        janggiPan.put(new Position(6, 6), new Piece(new Byeong()));
        janggiPan.put(new Position(6, 8), new Piece(new Byeong()));

        return new JanggiPan(janggiPan);
    }
}
