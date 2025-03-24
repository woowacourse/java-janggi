package pieceProperty;

import java.util.ArrayList;
import java.util.List;
import piece.Byeong;
import piece.Cha;
import piece.Janggun;
import piece.Jol;
import piece.Ma;
import piece.Piece;
import piece.Po;
import piece.Sa;
import piece.Sang;
import player.Pieces;

public class JanggiPieceInitializer {

    public Pieces hanInit() {
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Cha(new Position(0, 0)));
        pieces.add(new Cha(new Position(0, 8)));

        pieces.add(new Sang(new Position(0, 1)));
        pieces.add(new Sang(new Position(0, 7)));

        pieces.add(new Ma(new Position(0, 2)));
        pieces.add(new Ma(new Position(0, 6)));

        pieces.add(new Sa(new Position(0, 3)));
        pieces.add(new Sa(new Position(0, 5)));

        pieces.add(new Janggun(new Position(1, 4)));

        pieces.add(new Po(new Position(2, 1)));
        pieces.add(new Po(new Position(2, 7)));

        pieces.add(new Byeong(new Position(3, 0)));
        pieces.add(new Byeong(new Position(3, 2)));
        pieces.add(new Byeong(new Position(3, 4)));
        pieces.add(new Byeong(new Position(3, 6)));
        pieces.add(new Byeong(new Position(3, 8)));

        return new Pieces(pieces);
    }

    public Pieces choInit() {
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Cha(new Position(9, 0)));
        pieces.add(new Cha(new Position(9, 8)));

        pieces.add(new Sang(new Position(9, 1)));
        pieces.add(new Sang(new Position(9, 7)));

        pieces.add(new Ma(new Position(9, 2)));
        pieces.add(new Ma(new Position(9, 6)));

        pieces.add(new Sa(new Position(9, 3)));
        pieces.add(new Sa(new Position(9, 5)));

        pieces.add(new Janggun(new Position(8, 4)));

        pieces.add(new Po(new Position(7, 1)));
        pieces.add(new Po(new Position(7, 7)));

        pieces.add(new Jol(new Position(6, 0)));
        pieces.add(new Jol(new Position(6, 2)));
        pieces.add(new Jol(new Position(6, 4)));
        pieces.add(new Jol(new Position(6, 6)));
        pieces.add(new Jol(new Position(6, 8)));

        return new Pieces(pieces);
    }
}
