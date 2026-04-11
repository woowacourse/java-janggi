package janggi.domain.board;

import janggi.domain.Team;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class BoardFactory {

    private BoardFactory() {
    }

    public static Board create(String hanBoardType, String choBoardType) {
        Map<Position, Piece> board = new LinkedHashMap<>();
        initializeEmpty(board);
        placeHan(board);
        placeCho(board);
        BoardSetup.from(hanBoardType, Team.HAN).apply(board);
        BoardSetup.from(choBoardType, Team.CHO).apply(board);
        return Board.from(board);
    }

    private static void initializeEmpty(Map<Position, Piece> board) {
        IntStream.rangeClosed(Row.ROW_LOWER_THRESH_HOLD, Row.ROW_UPPER_THRESH_HOLD)
                .forEach(row -> initializeRow(board, row));
    }

    private static void initializeRow(Map<Position, Piece> board, int row) {
        IntStream.rangeClosed(Column.COLUMN_LOWER_THRESH_HOLD, Column.COLUMN_UPPER_THRESH_HOLD)
                .forEach(col -> board.put(Position.of(row, col), new EmptyPiece()));
    }

    private static void placeHan(Map<Position, Piece> board) {
        board.put(Position.from("11"), new Chariot(Team.HAN));
        board.put(Position.from("12"), new Elephant(Team.HAN));
        board.put(Position.from("13"), new Horse(Team.HAN));
        board.put(Position.from("14"), new Guard(Team.HAN));
        board.put(Position.from("16"), new Guard(Team.HAN));
        board.put(Position.from("17"), new Horse(Team.HAN));
        board.put(Position.from("18"), new Elephant(Team.HAN));
        board.put(Position.from("19"), new Chariot(Team.HAN));
        board.put(Position.from("25"), new General(Team.HAN));
        board.put(Position.from("32"), new Cannon(Team.HAN));
        board.put(Position.from("38"), new Cannon(Team.HAN));
        board.put(Position.from("41"), new Soldier(Team.HAN));
        board.put(Position.from("43"), new Soldier(Team.HAN));
        board.put(Position.from("45"), new Soldier(Team.HAN));
        board.put(Position.from("47"), new Soldier(Team.HAN));
        board.put(Position.from("49"), new Soldier(Team.HAN));
    }

    private static void placeCho(Map<Position, Piece> board) {
        board.put(Position.from("01"), new Chariot(Team.CHO));
        board.put(Position.from("02"), new Elephant(Team.CHO));
        board.put(Position.from("03"), new Horse(Team.CHO));
        board.put(Position.from("04"), new Guard(Team.CHO));
        board.put(Position.from("06"), new Guard(Team.CHO));
        board.put(Position.from("07"), new Horse(Team.CHO));
        board.put(Position.from("08"), new Elephant(Team.CHO));
        board.put(Position.from("09"), new Chariot(Team.CHO));
        board.put(Position.from("95"), new General(Team.CHO));
        board.put(Position.from("82"), new Cannon(Team.CHO));
        board.put(Position.from("88"), new Cannon(Team.CHO));
        board.put(Position.from("71"), new Soldier(Team.CHO));
        board.put(Position.from("73"), new Soldier(Team.CHO));
        board.put(Position.from("75"), new Soldier(Team.CHO));
        board.put(Position.from("77"), new Soldier(Team.CHO));
        board.put(Position.from("79"), new Soldier(Team.CHO));
    }
}
