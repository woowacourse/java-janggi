package janggi.domain;

import janggi.domain.movestrategy.ChaMoveStrategy;
import janggi.domain.movestrategy.GungseongBoundMoveStrategy;
import janggi.domain.movestrategy.JolMoveStrategy;
import janggi.domain.movestrategy.MaMoveStrategy;
import janggi.domain.movestrategy.PoMoveStrategy;
import janggi.domain.movestrategy.SangMoveStrategy;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;

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
                new Piece(new ChaMoveStrategy(), Team.HAN, PieceType.CHA));
        board.put(Position.of(Row.of(0), Column.of(8)),
                new Piece(new ChaMoveStrategy(), Team.HAN, PieceType.CHA));
        // 상
        board.put(Position.of(Row.of(0), Column.of(1)),
                new Piece(new SangMoveStrategy(), Team.HAN, PieceType.SANG));
        board.put(Position.of(Row.of(0), Column.of(6)),
                new Piece(new SangMoveStrategy(), Team.HAN, PieceType.SANG));
        // 마
        board.put(Position.of(Row.of(0), Column.of(2)),
                new Piece(new MaMoveStrategy(), Team.HAN, PieceType.MA));
        board.put(Position.of(Row.of(0), Column.of(7)),
                new Piece(new MaMoveStrategy(), Team.HAN, PieceType.MA));
        // 사
        board.put(Position.of(Row.of(0), Column.of(3)),
                new Piece(new GungseongBoundMoveStrategy(), Team.HAN, PieceType.SA));
        board.put(Position.of(Row.of(0), Column.of(5)),
                new Piece(new GungseongBoundMoveStrategy(), Team.HAN, PieceType.SA));
        // 궁
        board.put(Position.of(Row.of(1), Column.of(4)),
                new Piece(new GungseongBoundMoveStrategy(), Team.HAN, PieceType.HAN_GUNG));
        // 포
        board.put(Position.of(Row.of(2), Column.of(1)),
                new Piece(new PoMoveStrategy(), Team.HAN, PieceType.PO));
        board.put(Position.of(Row.of(2), Column.of(7)),
                new Piece(new PoMoveStrategy(), Team.HAN, PieceType.PO));
        // 졸
        board.put(Position.of(Row.of(3), Column.of(0)),
                new Piece(new JolMoveStrategy(), Team.HAN, PieceType.HAN_JOL));
        board.put(Position.of(Row.of(3), Column.of(2)),
                new Piece(new JolMoveStrategy(), Team.HAN, PieceType.HAN_JOL));
        board.put(Position.of(Row.of(3), Column.of(4)),
                new Piece(new JolMoveStrategy(), Team.HAN, PieceType.HAN_JOL));
        board.put(Position.of(Row.of(3), Column.of(6)),
                new Piece(new JolMoveStrategy(), Team.HAN, PieceType.HAN_JOL));
        board.put(Position.of(Row.of(3), Column.of(8)),
                new Piece(new JolMoveStrategy(), Team.HAN, PieceType.HAN_JOL));
    }

    private static void SetChoPieces(Map<Position, Piece> board) {
        // 차
        board.put(Position.of(Row.of(9), Column.of(0)),
                new Piece(new ChaMoveStrategy(), Team.CHO, PieceType.CHA));
        board.put(Position.of(Row.of(9), Column.of(8)),
                new Piece(new ChaMoveStrategy(), Team.CHO, PieceType.CHA));
        // 상
        board.put(Position.of(Row.of(9), Column.of(1)),
                new Piece(new SangMoveStrategy(), Team.CHO, PieceType.SANG));
        board.put(Position.of(Row.of(9), Column.of(6)),
                new Piece(new SangMoveStrategy(), Team.CHO, PieceType.SANG));
        // 마
        board.put(Position.of(Row.of(9), Column.of(2)),
                new Piece(new MaMoveStrategy(), Team.CHO, PieceType.MA));
        board.put(Position.of(Row.of(9), Column.of(7)),
                new Piece(new MaMoveStrategy(), Team.CHO, PieceType.MA));
        // 사
        board.put(Position.of(Row.of(9), Column.of(3)),
                new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.SA));
        board.put(Position.of(Row.of(9), Column.of(5)),
                new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.SA));
        // 궁
        board.put(Position.of(Row.of(8), Column.of(4)),
                new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.CHO_GUNG));
        // 포
        board.put(Position.of(Row.of(7), Column.of(1)),
                new Piece(new PoMoveStrategy(), Team.CHO, PieceType.PO));
        board.put(Position.of(Row.of(7), Column.of(7)),
                new Piece(new PoMoveStrategy(), Team.CHO, PieceType.PO));
        // 졸
        board.put(Position.of(Row.of(6), Column.of(0)),
                new Piece(new JolMoveStrategy(), Team.CHO, PieceType.CHO_JOL));
        board.put(Position.of(Row.of(6), Column.of(2)),
                new Piece(new JolMoveStrategy(), Team.CHO, PieceType.CHO_JOL));
        board.put(Position.of(Row.of(6), Column.of(4)),
                new Piece(new JolMoveStrategy(), Team.CHO, PieceType.CHO_JOL));
        board.put(Position.of(Row.of(6), Column.of(6)),
                new Piece(new JolMoveStrategy(), Team.CHO, PieceType.CHO_JOL));
        board.put(Position.of(Row.of(6), Column.of(8)),
                new Piece(new JolMoveStrategy(), Team.CHO, PieceType.CHO_JOL));
    }
}
