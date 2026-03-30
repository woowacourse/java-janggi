package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.Team;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import java.util.LinkedHashMap;
import java.util.Map;

public class BoardFactory {

    private BoardFactory() {

    }

    public static Board create(String hanBoardType, String choBoardType) {
        Map<Position, Piece> board = new LinkedHashMap<>();
        initializeEmpty(board);
        placeHan(board);
        placeCho(board);
        applyHanSetUp(board, hanBoardType);
        applyChoSetUp(board, choBoardType);
        return new Board(board);
    }

    private static void initializeEmpty(Map<Position, Piece> board) {
        for (int row = Row.ROW_LOWER_THRESH_HOLD; row <= Row.ROW_UPPER_THRESH_HOLD; row++) {
            for (int col = Column.COLUMN_LOWER_THRESH_HOLD; col <= Column.COLUMN_UPPER_THRESH_HOLD; col++) {
                int rowInput = row;
                if (row == 10) rowInput = 0;
                board.put(Position.from("" + rowInput + col), new EmptyPiece());
            }
        }
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

    private static void applyHanSetUp(Map<Position, Piece> board, String hanBoardType) {
        if (hanBoardType.equals("1")) {
            swap(board, Position.from("12"), Position.from("13"));
            return;
        }
        if (hanBoardType.equals("2")) {
            swap(board, Position.from("17"), Position.from("18"));
            return;
        }
        if (hanBoardType.equals("3")) {
            swap(board, Position.from("17"), Position.from("18"));
            swap(board, Position.from("12"), Position.from("13"));
        }
    }

    private static void applyChoSetUp(Map<Position, Piece> board, String choBoardType) {
        if (choBoardType.equals("1")) {
            swap(board, Position.from("07"), Position.from("08"));
            return;
        }
        if (choBoardType.equals("2")) {
            swap(board, Position.from("02"), Position.from("03"));
            return;
        }
        if (choBoardType.equals("3")) {
            swap(board, Position.from("07"), Position.from("08"));
            swap(board, Position.from("02"), Position.from("03"));
        }
    }

    private static void swap(Map<Position, Piece> board, Position position1, Position position2) {
        Piece piece1 = board.get(position1);
        Piece piece2 = board.get(position2);

        board.put(position1, piece2);
        board.put(position2, piece1);
    }
}
