package testUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Board;
import domain.Position;
import domain.enums.Country;
import domain.enums.PieceType;
import domain.pieces.Cha;
import domain.pieces.Jang;
import domain.pieces.Jol;
import domain.pieces.Ma;
import domain.pieces.Piece;
import domain.pieces.Po;
import domain.pieces.Sa;
import domain.pieces.Sang;

public final class BoardTestUtil {

    public static List<PieceType> createMasangSangMa(){
        return new ArrayList<>(
                List.of(PieceType.MA,PieceType.SANG,PieceType.SANG,PieceType.MA,
                        PieceType.MA,PieceType.SANG,PieceType.SANG,PieceType.MA)
        );
    }

    public static List<PieceType> createSangMaSangMa(){
        return new ArrayList<>(
                List.of(PieceType.SANG,PieceType.MA,PieceType.SANG,PieceType.MA,
                        PieceType.SANG,PieceType.MA,PieceType.SANG,PieceType.MA)
        );
    }

//    public static Board defaultBoard() {
//        Map<Position, Piece> board = new HashMap<>();
//
//        // 위쪽 (초)
//        board.put(Position.create(1,1), new Cha(Country.CHO));
//        board.put(Position.create(2,1), new Ma(Country.CHO));
//        board.put(Position.create(3,1), new Sang(Country.CHO));
//        board.put(Position.create(4,1), new Sa(Country.CHO));
//        board.put(Position.create(6,1), new Sa(Country.CHO));
//        board.put(Position.create(7,1), new Sang(Country.CHO));
//        board.put(Position.create(8,1), new Ma(Country.CHO));
//        board.put(Position.create(9,1), new Cha(Country.CHO));
//
//        board.put(Position.create(5,2), new Po(Country.CHO));
//
//        board.put(Position.create(2,3), new Po(Country.CHO));
//        board.put(Position.create(8,3), new Po(Country.CHO));
//
//        board.put(Position.create(1,4), new Jol(Country.CHO));
//        board.put(Position.create(7,4), new Jol(Country.CHO));
//        board.put(Position.create(9,4), new Jol(Country.CHO));
//
//        board.put(Position.create(3,5), new Jol(Country.CHO));
//        board.put(Position.create(4,5), new Jol(Country.CHO));
//
//        // 아래쪽 (한)
//        board.put(Position.create(1,10), new Cha(Country.HAN));
//        board.put(Position.create(3,10), new Sang(Country.HAN));
//        board.put(Position.create(4,10), new Sa(Country.HAN));
//        board.put(Position.create(6,10), new Sa(Country.HAN));
//        board.put(Position.create(7,10), new Sang(Country.HAN));
//        board.put(Position.create(8,10), new Ma(Country.HAN));
//        board.put(Position.create(9,10), new Cha(Country.HAN));
//
//        board.put(Position.create(5,9), new Jang(Country.HAN));
//
//        board.put(Position.create(3,8), new Ma(Country.HAN));
//        board.put(Position.create(8,8), new Po(Country.HAN));
//
//        board.put(Position.create(1,7), new Jol(Country.HAN));
//        board.put(Position.create(3,7), new Jol(Country.HAN));
//        board.put(Position.create(5,7), new Jol(Country.HAN));
//        board.put(Position.create(7,7), new Jol(Country.HAN));
//        board.put(Position.create(9,7), new Jol(Country.HAN));
//
//        return new Board(board);
//    }
}

