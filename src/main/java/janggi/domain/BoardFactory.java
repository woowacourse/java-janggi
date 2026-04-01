package janggi.domain;

import janggi.domain.movestorage.ChaMoveStorage;
import janggi.domain.movestorage.GungAndSaMoveStorage;
import janggi.domain.movestorage.JolMoveStorage;
import janggi.domain.movestorage.MaMoveStorage;
import janggi.domain.movestorage.PoMoveStorage;
import janggi.domain.movestorage.SangMoveStorage;

import java.util.HashMap;
import java.util.Map;

public class BoardFactory {
    public static Map<Position, Piece> generate() {
        Map<Position, Piece> board = new HashMap<>();

        SetHanPieces(board);
        SetChoPieces(board);

        return board;
    }

    private static void SetHanPieces(Map<Position, Piece> board) {
        // 차
        board.put(Position.of(Row.of(0), Column.of(0)),
                new Piece(new ChaMoveStorage(), Team.HAN, 13, "車"));
        board.put(Position.of(Row.of(8), Column.of(0)),
                new Piece(new ChaMoveStorage(), Team.HAN, 13, "車"));
        // 상
        board.put(Position.of(Row.of(1), Column.of(0)),
                new Piece(new SangMoveStorage(), Team.HAN, 3, "象"));
        board.put(Position.of(Row.of(6), Column.of(0)),
                new Piece(new SangMoveStorage(), Team.HAN, 3, "象"));
        // 마
        board.put(Position.of(Row.of(2), Column.of(0)),
                new Piece(new MaMoveStorage(), Team.HAN, 5, "馬"));
        board.put(Position.of(Row.of(7), Column.of(0)),
                new Piece(new MaMoveStorage(), Team.HAN, 5, "馬"));
        // 사
        board.put(Position.of(Row.of(3), Column.of(0)),
                new Piece(new GungAndSaMoveStorage(), Team.HAN, 3, "士"));
        board.put(Position.of(Row.of(5), Column.of(0)),
                new Piece(new GungAndSaMoveStorage(), Team.HAN, 3, "士"));
        // 궁
        board.put(Position.of(Row.of(4), Column.of(1)),
                new Piece(new GungAndSaMoveStorage(), Team.HAN, 0, "漢"));
        // 포
        board.put(Position.of(Row.of(1), Column.of(2)),
                new Piece(new PoMoveStorage(), Team.HAN, 7, "包"));
        board.put(Position.of(Row.of(7), Column.of(2)),
                new Piece(new PoMoveStorage(), Team.HAN, 7, "包"));
        // 졸
        board.put(Position.of(Row.of(0), Column.of(3)),
                new Piece(new JolMoveStorage(), Team.HAN, 2, "兵"));
        board.put(Position.of(Row.of(2), Column.of(3)),
                new Piece(new JolMoveStorage(), Team.HAN, 2, "兵"));
        board.put(Position.of(Row.of(4), Column.of(3)),
                new Piece(new JolMoveStorage(), Team.HAN, 2, "兵"));
        board.put(Position.of(Row.of(6), Column.of(3)),
                new Piece(new JolMoveStorage(), Team.HAN, 2, "兵"));
        board.put(Position.of(Row.of(8), Column.of(3)),
                new Piece(new JolMoveStorage(), Team.HAN, 2, "兵"));
    }

    private static void SetChoPieces(Map<Position, Piece> board) {
        // 차
        board.put(Position.of(Row.of(0), Column.of(9)),
                new Piece(new ChaMoveStorage(), Team.CHO, 13, "車"));
        board.put(Position.of(Row.of(8), Column.of(9)),
                new Piece(new ChaMoveStorage(), Team.CHO, 13, "車"));
        // 상
        board.put(Position.of(Row.of(1), Column.of(9)),
                new Piece(new SangMoveStorage(), Team.CHO, 3, "象"));
        board.put(Position.of(Row.of(6), Column.of(9)),
                new Piece(new SangMoveStorage(), Team.CHO, 3, "象"));
        // 마
        board.put(Position.of(Row.of(2), Column.of(9)),
                new Piece(new MaMoveStorage(), Team.CHO, 5, "馬"));
        board.put(Position.of(Row.of(7), Column.of(9)),
                new Piece(new MaMoveStorage(), Team.CHO, 5, "馬"));
        // 사
        board.put(Position.of(Row.of(3), Column.of(9)),
                new Piece(new GungAndSaMoveStorage(), Team.CHO, 3, "士"));
        board.put(Position.of(Row.of(5), Column.of(9)),
                new Piece(new GungAndSaMoveStorage(), Team.CHO, 3, "士"));
        // 궁
        board.put(Position.of(Row.of(4), Column.of(8)),
                new Piece(new GungAndSaMoveStorage(), Team.CHO, 0, "楚"));
        // 포
        board.put(Position.of(Row.of(1), Column.of(7)),
                new Piece(new SangMoveStorage(), Team.CHO, 7, "包"));
        board.put(Position.of(Row.of(7), Column.of(7)),
                new Piece(new SangMoveStorage(), Team.CHO, 7, "包"));
        // 졸
        board.put(Position.of(Row.of(0), Column.of(6)),
                new Piece(new JolMoveStorage(), Team.CHO, 2, "卒"));
        board.put(Position.of(Row.of(2), Column.of(6)),
                new Piece(new JolMoveStorage(), Team.CHO, 2, "卒"));
        board.put(Position.of(Row.of(4), Column.of(6)),
                new Piece(new JolMoveStorage(), Team.CHO, 2,  "卒"));
        board.put(Position.of(Row.of(6), Column.of(6)),
                new Piece(new JolMoveStorage(), Team.CHO, 2,  "卒"));
        board.put(Position.of(Row.of(8), Column.of(6)),
                new Piece(new JolMoveStorage(), Team.CHO, 2,  "卒"));
    }
}
