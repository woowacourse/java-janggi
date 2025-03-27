package janggi.board;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.piece.multiplemovepiece.Cha;
import janggi.piece.multiplemovepiece.Ma;
import janggi.piece.multiplemovepiece.Po;
import janggi.piece.multiplemovepiece.Sang;
import janggi.piece.onemovepiece.Byeong;
import janggi.piece.onemovepiece.Janggun;
import janggi.piece.onemovepiece.Jol;
import janggi.piece.onemovepiece.Sa;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public class PieceInitializer {

    public List<Piece> generate() {
        final List<Piece> pieces = new ArrayList<>();

        pieces.add(new Cha(Team.HAN, new Position(0, 0)));
        pieces.add(new Cha(Team.HAN, new Position(0, 8)));

        pieces.add(new Sang(Team.HAN, new Position(0, 1)));
        pieces.add(new Sang(Team.HAN, new Position(0, 7)));

        pieces.add(new Ma(Team.HAN, new Position(0, 2)));
        pieces.add(new Ma(Team.HAN, new Position(0, 6)));

        pieces.add(new Sa(Team.HAN, new Position(0, 3)));
        pieces.add(new Sa(Team.HAN, new Position(0, 5)));

        pieces.add(new Janggun(Team.HAN, new Position(1, 4)));

        pieces.add(new Po(Team.HAN, new Position(2, 1)));
        pieces.add(new Po(Team.HAN, new Position(2, 7)));

        pieces.add(new Byeong(Team.HAN, new Position(3, 0)));
        pieces.add(new Byeong(Team.HAN, new Position(3, 2)));
        pieces.add(new Byeong(Team.HAN, new Position(3, 4)));
        pieces.add(new Byeong(Team.HAN, new Position(3, 6)));
        pieces.add(new Byeong(Team.HAN, new Position(3, 8)));

        pieces.add(new Cha(Team.CHO, new Position(9, 0)));
        pieces.add(new Cha(Team.CHO, new Position(9, 8)));
        pieces.add(new Sang(Team.CHO, new Position(9, 1)));
        pieces.add(new Sang(Team.CHO, new Position(9, 7)));

        pieces.add(new Ma(Team.CHO, new Position(9, 2)));
        pieces.add(new Ma(Team.CHO, new Position(9, 6)));

        pieces.add(new Sa(Team.CHO, new Position(9, 3)));
        pieces.add(new Sa(Team.CHO, new Position(9, 5)));

        pieces.add(new Janggun(Team.CHO, new Position(8, 4)));

        pieces.add(new Po(Team.CHO, new Position(7, 1)));
        pieces.add(new Po(Team.CHO, new Position(7, 7)));

        pieces.add(new Jol(Team.CHO, new Position(6, 0)));
        pieces.add(new Jol(Team.CHO, new Position(6, 2)));
        pieces.add(new Jol(Team.CHO, new Position(6, 4)));
        pieces.add(new Jol(Team.CHO, new Position(6, 6)));
        pieces.add(new Jol(Team.CHO, new Position(6, 8)));

        return pieces;
    }

}
