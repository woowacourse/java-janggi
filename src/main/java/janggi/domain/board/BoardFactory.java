package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;

import java.util.HashMap;
import java.util.Map;

public class BoardFactory {
    public static Map<Position, Piece> generate() {
        Map<Position, Piece> board = new HashMap<>();

        setHanPieces(board);
        setChoPieces(board);

        return board;
    }

    private static void setHanPieces(Map<Position, Piece> board) {
        // 차
        board.put(Position.of(Row.of(0), Column.of(0)),
                new Piece(Team.HAN, PieceType.CHA));
        board.put(Position.of(Row.of(0), Column.of(8)),
                new Piece(Team.HAN, PieceType.CHA));
        // 상
        board.put(Position.of(Row.of(0), Column.of(1)),
                new Piece(Team.HAN, PieceType.SANG));
        board.put(Position.of(Row.of(0), Column.of(6)),
                new Piece(Team.HAN, PieceType.SANG));
        // 마
        board.put(Position.of(Row.of(0), Column.of(2)),
                new Piece(Team.HAN, PieceType.MA));
        board.put(Position.of(Row.of(0), Column.of(7)),
                new Piece(Team.HAN, PieceType.MA));
        // 사
        board.put(Position.of(Row.of(0), Column.of(3)),
                new Piece(Team.HAN, PieceType.SA));
        board.put(Position.of(Row.of(0), Column.of(5)),
                new Piece(Team.HAN, PieceType.SA));
        // 궁
        board.put(Position.of(Row.of(1), Column.of(4)),
                new Piece(Team.HAN, PieceType.HAN_GUNG));
        // 포
        board.put(Position.of(Row.of(2), Column.of(1)),
                new Piece(Team.HAN, PieceType.PO));
        board.put(Position.of(Row.of(2), Column.of(7)),
                new Piece(Team.HAN, PieceType.PO));
        // 졸
        board.put(Position.of(Row.of(3), Column.of(0)),
                new Piece(Team.HAN, PieceType.HAN_JOL));
        board.put(Position.of(Row.of(3), Column.of(2)),
                new Piece(Team.HAN, PieceType.HAN_JOL));
        board.put(Position.of(Row.of(3), Column.of(4)),
                new Piece(Team.HAN, PieceType.HAN_JOL));
        board.put(Position.of(Row.of(3), Column.of(6)),
                new Piece(Team.HAN, PieceType.HAN_JOL));
        board.put(Position.of(Row.of(3), Column.of(8)),
                new Piece(Team.HAN, PieceType.HAN_JOL));
    }

    private static void setChoPieces(Map<Position, Piece> board) {
        // 차
        board.put(Position.of(Row.of(9), Column.of(0)),
                new Piece(Team.CHO, PieceType.CHA));
        board.put(Position.of(Row.of(9), Column.of(8)),
                new Piece(Team.CHO, PieceType.CHA));
        // 상
        board.put(Position.of(Row.of(9), Column.of(1)),
                new Piece(Team.CHO, PieceType.SANG));
        board.put(Position.of(Row.of(9), Column.of(6)),
                new Piece(Team.CHO, PieceType.SANG));
        // 마
        board.put(Position.of(Row.of(9), Column.of(2)),
                new Piece(Team.CHO, PieceType.MA));
        board.put(Position.of(Row.of(9), Column.of(7)),
                new Piece(Team.CHO, PieceType.MA));
        // 사
        board.put(Position.of(Row.of(9), Column.of(3)),
                new Piece(Team.CHO, PieceType.SA));
        board.put(Position.of(Row.of(9), Column.of(5)),
                new Piece(Team.CHO, PieceType.SA));
        // 궁
        board.put(Position.of(Row.of(8), Column.of(4)),
                new Piece(Team.CHO, PieceType.CHO_GUNG));
        // 포
        board.put(Position.of(Row.of(7), Column.of(1)),
                new Piece(Team.CHO, PieceType.PO));
        board.put(Position.of(Row.of(7), Column.of(7)),
                new Piece(Team.CHO, PieceType.PO));
        // 졸
        board.put(Position.of(Row.of(6), Column.of(0)),
                new Piece(Team.CHO, PieceType.CHO_JOL));
        board.put(Position.of(Row.of(6), Column.of(2)),
                new Piece(Team.CHO, PieceType.CHO_JOL));
        board.put(Position.of(Row.of(6), Column.of(4)),
                new Piece(Team.CHO, PieceType.CHO_JOL));
        board.put(Position.of(Row.of(6), Column.of(6)),
                new Piece(Team.CHO, PieceType.CHO_JOL));
        board.put(Position.of(Row.of(6), Column.of(8)),
                new Piece(Team.CHO, PieceType.CHO_JOL));
    }
}
