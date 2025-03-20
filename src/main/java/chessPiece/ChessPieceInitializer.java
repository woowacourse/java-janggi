package chessPiece;

import java.util.ArrayList;
import java.util.List;

public class ChessPieceInitializer {

    public List<ChessPiece> hanInit() {
        List<ChessPiece> chessPieces = new ArrayList<>();

        chessPieces.add(new Cha(new PieceProfile("차", Nation.HAN), new BoardPosition(0, 0)));
        chessPieces.add(new Cha(new PieceProfile("차", Nation.HAN), new BoardPosition(0, 8)));

        chessPieces.add(new Sang(new PieceProfile("상", Nation.HAN), new BoardPosition(0, 1)));
        chessPieces.add(new Sang(new PieceProfile("상", Nation.HAN), new BoardPosition(0, 7)));

        chessPieces.add(new Ma(new PieceProfile("마", Nation.HAN), new BoardPosition(0, 2)));
        chessPieces.add(new Ma(new PieceProfile("마", Nation.HAN), new BoardPosition(0, 6)));

        chessPieces.add(new Sa(new PieceProfile("사", Nation.HAN), new BoardPosition(0, 3)));
        chessPieces.add(new Sa(new PieceProfile("사", Nation.HAN), new BoardPosition(0, 5)));

        chessPieces.add(new Janggun(new PieceProfile("왕", Nation.HAN), new BoardPosition(1, 4)));

        chessPieces.add(new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(2, 1)));
        chessPieces.add(new Po(new PieceProfile("포", Nation.HAN), new BoardPosition(2, 7)));

        chessPieces.add(new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(3, 0)));
        chessPieces.add(new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(3, 2)));
        chessPieces.add(new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(3, 4)));
        chessPieces.add(new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(3, 6)));
        chessPieces.add(new Byeong(new PieceProfile("병", Nation.HAN), new BoardPosition(3, 8)));

        return chessPieces;
    }

    public List<ChessPiece> choInit() {
        List<ChessPiece> chessPieces = new ArrayList<>();

        chessPieces.add(new Cha(new PieceProfile("차", Nation.CHO), new BoardPosition(9, 0)));
        chessPieces.add(new Cha(new PieceProfile("차", Nation.CHO), new BoardPosition(9, 8)));

        chessPieces.add(new Sang(new PieceProfile("상", Nation.CHO), new BoardPosition(9, 1)));
        chessPieces.add(new Sang(new PieceProfile("상", Nation.CHO), new BoardPosition(9, 7)));

        chessPieces.add(new Ma(new PieceProfile("마", Nation.CHO), new BoardPosition(9, 2)));
        chessPieces.add(new Ma(new PieceProfile("마", Nation.CHO), new BoardPosition(9, 6)));

        chessPieces.add(new Sa(new PieceProfile("사", Nation.CHO), new BoardPosition(9, 3)));
        chessPieces.add(new Sa(new PieceProfile("사", Nation.CHO), new BoardPosition(9, 5)));

        chessPieces.add(new Janggun(new PieceProfile("왕", Nation.CHO), new BoardPosition(8, 4)));

        chessPieces.add(new Po(new PieceProfile("포", Nation.CHO), new BoardPosition(7, 1)));
        chessPieces.add(new Po(new PieceProfile("포", Nation.CHO), new BoardPosition(7, 7)));

        chessPieces.add(new Jol(new PieceProfile("졸", Nation.CHO), new BoardPosition(6, 0)));
        chessPieces.add(new Jol(new PieceProfile("졸", Nation.CHO), new BoardPosition(6, 2)));
        chessPieces.add(new Jol(new PieceProfile("졸", Nation.CHO), new BoardPosition(6, 4)));
        chessPieces.add(new Jol(new PieceProfile("졸", Nation.CHO), new BoardPosition(6, 6)));
        chessPieces.add(new Jol(new PieceProfile("졸", Nation.CHO), new BoardPosition(6, 8)));

        return chessPieces;
    }
}
