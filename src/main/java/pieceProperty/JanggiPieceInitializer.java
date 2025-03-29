package pieceProperty;

import java.util.ArrayList;
import java.util.List;
import piece.Byeong;
import piece.Cha;
import piece.Janggun;
import piece.Jol;
import piece.Ma;
import piece.PieceRule;
import piece.Po;
import piece.Sa;
import piece.Sang;
import player.Pieces;

public class JanggiPieceInitializer {

    public Pieces hanInit() {
        List<PieceRule> pieceRules = new ArrayList<>();

        pieceRules.add(new Cha(new Position(0, 0)));
        pieceRules.add(new Cha(new Position(0, 8)));

        pieceRules.add(new Sang(new Position(0, 1)));
        pieceRules.add(new Sang(new Position(0, 7)));

        pieceRules.add(new Ma(new Position(0, 2)));
        pieceRules.add(new Ma(new Position(0, 6)));

        pieceRules.add(new Sa(new Position(0, 3)));
        pieceRules.add(new Sa(new Position(0, 5)));

        pieceRules.add(new Janggun(new Position(1, 4)));

        pieceRules.add(new Po(new Position(2, 1)));
        pieceRules.add(new Po(new Position(2, 7)));

        pieceRules.add(new Byeong(new Position(3, 0)));
        pieceRules.add(new Byeong(new Position(3, 2)));
        pieceRules.add(new Byeong(new Position(3, 4)));
        pieceRules.add(new Byeong(new Position(3, 6)));
        pieceRules.add(new Byeong(new Position(3, 8)));

        return new Pieces(pieceRules);
    }

    public Pieces choInit() {
        List<PieceRule> pieceRules = new ArrayList<>();

        pieceRules.add(new Cha(new Position(9, 0)));
        pieceRules.add(new Cha(new Position(9, 8)));

        pieceRules.add(new Sang(new Position(9, 1)));
        pieceRules.add(new Sang(new Position(9, 7)));

        pieceRules.add(new Ma(new Position(9, 2)));
        pieceRules.add(new Ma(new Position(9, 6)));

        pieceRules.add(new Sa(new Position(9, 3)));
        pieceRules.add(new Sa(new Position(9, 5)));

        pieceRules.add(new Janggun(new Position(8, 4)));

        pieceRules.add(new Po(new Position(7, 1)));
        pieceRules.add(new Po(new Position(7, 7)));

        pieceRules.add(new Jol(new Position(6, 0)));
        pieceRules.add(new Jol(new Position(6, 2)));
        pieceRules.add(new Jol(new Position(6, 4)));
        pieceRules.add(new Jol(new Position(6, 6)));
        pieceRules.add(new Jol(new Position(6, 8)));

        return new Pieces(pieceRules);
    }
}
