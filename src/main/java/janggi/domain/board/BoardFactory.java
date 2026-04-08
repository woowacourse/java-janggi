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
        final int CHO_ZOL_ROW = 7;
        final int HAN_ZOL_ROW = 4;

        List<Integer> zolPositions = List.of(1, 3, 5, 7, 9);

        for (int column : zolPositions) {
            board.put(new Position(column, CHO_ZOL_ROW), new Piece(Team.CHO, PieceType.ZOL));
            board.put(new Position(column, HAN_ZOL_ROW), new Piece(Team.HAN, PieceType.ZOL));
        }
    }

    private static void settingUpPo(Map<Position, Piece> board) {
        final int CHO_PO_ROW = 8;
        final int HAN_PO_ROW = 3;

        List<Integer> poPositions = List.of(2, 8);

        for (int column : poPositions) {
            board.put(new Position(column, CHO_PO_ROW), new Piece(Team.CHO, PieceType.PO));
            board.put(new Position(column, HAN_PO_ROW), new Piece(Team.HAN, PieceType.PO));
        }
    }

    private static void settingUpCha(Map<Position, Piece> board) {
        final int CHO_CHA_ROW = 10;
        final int HAN_CHA_ROW = 1;

        List<Integer> chaPositions = List.of(1, 9);

        for (int column : chaPositions) {
            board.put(new Position(column, CHO_CHA_ROW), new Piece(Team.CHO, PieceType.CHA));
            board.put(new Position(column, HAN_CHA_ROW), new Piece(Team.HAN, PieceType.CHA));
        }
    }

    private static void settingUpMa(Map<Position, Piece> board) {
        final int CHO_MA_ROW = 10;
        final int HAN_MA_ROW = 1;

        List<Integer> chaPositions = List.of(2, 7);

        for (int column : chaPositions) {
            board.put(new Position(column, CHO_MA_ROW), new Piece(Team.CHO, PieceType.MA));
            board.put(new Position(column, HAN_MA_ROW), new Piece(Team.HAN, PieceType.MA));
        }
    }

    private static void settingUpSang(Map<Position, Piece> board) {
        final int CHO_SANG_ROW = 10;
        final int HAN_SANG_ROW = 1;

        List<Integer> chaPositions = List.of(3, 8);

        for (int column : chaPositions) {
            board.put(new Position(column, CHO_SANG_ROW), new Piece(Team.CHO, PieceType.SANG));
            board.put(new Position(column, HAN_SANG_ROW), new Piece(Team.HAN, PieceType.SANG));
        }
    }

    private static void settingUpSa(Map<Position, Piece> board) {
        final int CHO_SA_ROW = 10;
        final int HAN_SA_ROW = 1;

        List<Integer> chaPositions = List.of(4, 6);

        for (int column : chaPositions) {
            board.put(new Position(column, CHO_SA_ROW), new Piece(Team.CHO, PieceType.SA));
            board.put(new Position(column, HAN_SA_ROW), new Piece(Team.HAN, PieceType.SA));
        }
    }

    private static void settingUpKing(Map<Position, Piece> board) {
        final int CHO_KING_COLUMN = 5;
        final int CHO_KING_ROW = 9;
        final int HAN_KING_COLUMN = 5;
        final int HAN_KING_ROW = 2;

        board.put(new Position(CHO_KING_COLUMN, CHO_KING_ROW), new Piece(Team.CHO, PieceType.KING));
        board.put(new Position(HAN_KING_COLUMN, HAN_KING_ROW), new Piece(Team.HAN, PieceType.KING));
    }
}
