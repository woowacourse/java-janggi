package janggi.domain;

import janggi.domain.movestorage.ChaMoveStorage;
import janggi.domain.movestorage.GungAndSaMoveStorage;
import janggi.domain.movestorage.JolMoveStorage;
import janggi.domain.movestorage.MaMoveStorage;
import janggi.domain.movestorage.MoveStorage;
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
        Team team = Team.HAN;

        putPiece(board, 0, 0, new ChaMoveStorage(), team, 13, "車");
        putPiece(board, 8, 0, new ChaMoveStorage(), team, 13, "車");

        putPiece(board, 1, 0, new SangMoveStorage(), team, 3, "象");
        putPiece(board, 6, 0, new SangMoveStorage(), team, 3, "象");

        putPiece(board, 2, 0, new MaMoveStorage(), team, 5, "馬");
        putPiece(board, 7, 0, new MaMoveStorage(), team, 5, "馬");

        putPiece(board, 3, 0, new GungAndSaMoveStorage(), team, 3, "士");
        putPiece(board, 5, 0, new GungAndSaMoveStorage(), team, 3, "士");

        putPiece(board, 4, 1, new GungAndSaMoveStorage(), team, 0, "漢");

        putPiece(board, 1, 2, new PoMoveStorage(), team, 7, "包");
        putPiece(board, 7, 2, new PoMoveStorage(), team, 7, "包");

        putPiece(board, 0, 3, new JolMoveStorage(), team, 2, "兵");
        putPiece(board, 2, 3, new JolMoveStorage(), team, 2, "兵");
        putPiece(board, 4, 3, new JolMoveStorage(), team, 2, "兵");
        putPiece(board, 6, 3, new JolMoveStorage(), team, 2, "兵");
        putPiece(board, 8, 3, new JolMoveStorage(), team, 2, "兵");
    }

    private static void SetChoPieces(Map<Position, Piece> board) {
        Team team = Team.CHO;

        putPiece(board, 0, 9, new ChaMoveStorage(), team, 13, "車");
        putPiece(board, 8, 9, new ChaMoveStorage(), team, 13, "車");

        putPiece(board, 1, 9, new SangMoveStorage(), team, 3, "象");
        putPiece(board, 6, 9, new SangMoveStorage(), team, 3, "象");

        putPiece(board, 2, 9, new MaMoveStorage(), team, 5, "馬");
        putPiece(board, 7, 9, new MaMoveStorage(), team, 5, "馬");

        putPiece(board, 3, 9, new GungAndSaMoveStorage(), team, 3, "士");
        putPiece(board, 5, 9, new GungAndSaMoveStorage(), team, 3, "士");

        putPiece(board, 4, 8, new GungAndSaMoveStorage(), team, 0, "楚");

        putPiece(board, 1, 7, new PoMoveStorage(), team, 7, "包");
        putPiece(board, 7, 7, new PoMoveStorage(), team, 7, "包");

        putPiece(board, 0, 6, new JolMoveStorage(), team, 2, "卒");
        putPiece(board, 2, 6, new JolMoveStorage(), team, 2, "卒");
        putPiece(board, 4, 6, new JolMoveStorage(), team, 2, "卒");
        putPiece(board, 6, 6, new JolMoveStorage(), team, 2, "卒");
        putPiece(board, 8, 6, new JolMoveStorage(), team, 2, "卒");
    }

    private static void putPiece(Map<Position, Piece> board, int row, int col, MoveStorage storage, Team team, int score, String name) {
        board.put(Position.of(Row.of(row), Column.of(col)), new Piece(storage, team, score, name));
    }
}
