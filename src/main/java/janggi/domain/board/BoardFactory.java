package janggi.domain.board;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {

    public static Map<Position, Piece> settingUpBoard() {
        Map<Position, Piece> board = new HashMap<>();
        settingUpZol(board);
        settingUpPo(board);
        settingUpCha(board);
        settingUpMa(board);
        settingUpSang(board);
        settingUpSa(board);
        settingUpKing(board);
        return board;
    }


    private static void settingUpZol(Map<Position, Piece> board) {
        final int CHO_ZOL_Y = 7;
        final int HAN_ZOL_Y = 4;

        List<Integer> zolPositions = List.of(1, 3, 5, 7, 9);

        for (int x : zolPositions) {
            board.put(new Position(x, CHO_ZOL_Y), new Piece(Team.CHO, PieceType.ZOL));
            board.put(new Position(x, HAN_ZOL_Y), new Piece(Team.HAN, PieceType.ZOL));
        }
    }

    private static void settingUpPo(Map<Position, Piece> board) {
        final int CHO_PO_Y = 8;
        final int HAN_PO_Y = 3;

        List<Integer> poPositions = List.of(2, 8);

        for (int x : poPositions) {
            board.put(new Position(x, CHO_PO_Y), new Piece(Team.CHO, PieceType.PO));
            board.put(new Position(x, HAN_PO_Y), new Piece(Team.HAN, PieceType.PO));
        }
    }

    private static void settingUpCha(Map<Position, Piece> board) {
        final int CHO_CHA_Y = 10;
        final int HAN_CHA_Y = 1;

        List<Integer> chaPositions = List.of(1, 9);

        for (int x : chaPositions) {
            board.put(new Position(x, CHO_CHA_Y), new Piece(Team.CHO, PieceType.CHA));
            board.put(new Position(x, HAN_CHA_Y), new Piece(Team.HAN, PieceType.CHA));
        }
    }

    private static void settingUpMa(Map<Position, Piece> board) {
        final int CHO_MA_Y = 10;
        final int HAN_MA_Y = 1;

        List<Integer> chaPositions = List.of(2, 7);

        for (int x : chaPositions) {
            board.put(new Position(x, CHO_MA_Y), new Piece(Team.CHO, PieceType.MA));
            board.put(new Position(x, HAN_MA_Y), new Piece(Team.HAN, PieceType.MA));
        }
    }

    private static void settingUpSang(Map<Position, Piece> board) {
        final int CHO_SANG_Y = 10;
        final int HAN_SANG_Y = 1;

        List<Integer> chaPositions = List.of(3, 8);

        for (int x : chaPositions) {
            board.put(new Position(x, CHO_SANG_Y), new Piece(Team.CHO, PieceType.SANG));
            board.put(new Position(x, HAN_SANG_Y), new Piece(Team.HAN, PieceType.SANG));
        }
    }

    private static void settingUpSa(Map<Position, Piece> board) {
        final int CHO_SA_Y = 10;
        final int HAN_SA_Y = 1;

        List<Integer> chaPositions = List.of(4, 6);

        for (int x : chaPositions) {
            board.put(new Position(x, CHO_SA_Y), new Piece(Team.CHO, PieceType.SA));
            board.put(new Position(x, HAN_SA_Y), new Piece(Team.HAN, PieceType.SA));
        }
    }

    private static void settingUpKing(Map<Position, Piece> board) {
        final int CHO_KING_X = 5;
        final int CHO_KING_Y = 9;
        final int HAN_KING_X = 5;
        final int HAN_KING_Y = 2;

        board.put(new Position(CHO_KING_X, CHO_KING_Y), new Piece(Team.CHO, PieceType.KING));
        board.put(new Position(HAN_KING_X, HAN_KING_Y), new Piece(Team.HAN, PieceType.KING));
    }
}
