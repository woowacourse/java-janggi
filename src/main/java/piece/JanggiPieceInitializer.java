package piece;

import java.util.ArrayList;
import java.util.List;

public class JanggiPieceInitializer {

    public List<Piece> hanInit() {
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Cha(new PieceProfile("차", Nation.HAN), new Position(0, 0)));
        pieces.add(new Cha(new PieceProfile("차", Nation.HAN), new Position(0, 8)));

        pieces.add(new Sang(new PieceProfile("상", Nation.HAN), new Position(0, 1)));
        pieces.add(new Sang(new PieceProfile("상", Nation.HAN), new Position(0, 7)));

        pieces.add(new Ma(new PieceProfile("마", Nation.HAN), new Position(0, 2)));
        pieces.add(new Ma(new PieceProfile("마", Nation.HAN), new Position(0, 6)));

        pieces.add(new Sa(new PieceProfile("사", Nation.HAN), new Position(0, 3)));
        pieces.add(new Sa(new PieceProfile("사", Nation.HAN), new Position(0, 5)));

        pieces.add(new Janggun(new PieceProfile("왕", Nation.HAN), new Position(1, 4)));

        pieces.add(new Po(new PieceProfile("포", Nation.HAN), new Position(2, 1)));
        pieces.add(new Po(new PieceProfile("포", Nation.HAN), new Position(2, 7)));

        pieces.add(new Byeong(new PieceProfile("병", Nation.HAN), new Position(3, 0)));
        pieces.add(new Byeong(new PieceProfile("병", Nation.HAN), new Position(3, 2)));
        pieces.add(new Byeong(new PieceProfile("병", Nation.HAN), new Position(3, 4)));
        pieces.add(new Byeong(new PieceProfile("병", Nation.HAN), new Position(3, 6)));
        pieces.add(new Byeong(new PieceProfile("병", Nation.HAN), new Position(3, 8)));

        return pieces;
    }

    public List<Piece> choInit() {
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Cha(new PieceProfile("차", Nation.CHO), new Position(9, 0)));
        pieces.add(new Cha(new PieceProfile("차", Nation.CHO), new Position(9, 8)));

        pieces.add(new Sang(new PieceProfile("상", Nation.CHO), new Position(9, 1)));
        pieces.add(new Sang(new PieceProfile("상", Nation.CHO), new Position(9, 7)));

        pieces.add(new Ma(new PieceProfile("마", Nation.CHO), new Position(9, 2)));
        pieces.add(new Ma(new PieceProfile("마", Nation.CHO), new Position(9, 6)));

        pieces.add(new Sa(new PieceProfile("사", Nation.CHO), new Position(9, 3)));
        pieces.add(new Sa(new PieceProfile("사", Nation.CHO), new Position(9, 5)));

        pieces.add(new Janggun(new PieceProfile("왕", Nation.CHO), new Position(8, 4)));

        pieces.add(new Po(new PieceProfile("포", Nation.CHO), new Position(7, 1)));
        pieces.add(new Po(new PieceProfile("포", Nation.CHO), new Position(7, 7)));

        pieces.add(new Jol(new PieceProfile("졸", Nation.CHO), new Position(6, 0)));
        pieces.add(new Jol(new PieceProfile("졸", Nation.CHO), new Position(6, 2)));
        pieces.add(new Jol(new PieceProfile("졸", Nation.CHO), new Position(6, 4)));
        pieces.add(new Jol(new PieceProfile("졸", Nation.CHO), new Position(6, 6)));
        pieces.add(new Jol(new PieceProfile("졸", Nation.CHO), new Position(6, 8)));

        return pieces;
    }
}
