package janggi.domain.board;

import janggi.domain.Team;
import janggi.domain.piece.*;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;

import java.util.LinkedHashMap;
import java.util.Map;

import static janggi.domain.board.PieceSetup.*;

public class BoardFactory {

    private BoardFactory() {

    }

    public static Board create(PieceSetup hanSetup, PieceSetup choSetup) {
        Map<Position, Piece> base = new LinkedHashMap<>();
        initializeEmpty(base);
        placeHan(base);
        placeCho(base);
        applySetUp(base, hanSetup, choSetup);
        return new Board(base);
    }

    private static void initializeEmpty(Map<Position, Piece> base) {
        for (int row = Row.ROW_LOWER_THRESH_HOLD; row <= Row.ROW_UPPER_THRESH_HOLD; row++) {
            for (int col = Column.COLUMN_LOWER_THRESH_HOLD; col <= Column.COLUMN_UPPER_THRESH_HOLD; col++) {
                base.put(Position.of(row, col), new EmptyPiece());
            }
        }
    }

    private static void placeHan(Map<Position, Piece> base) {
        base.put(Position.from("11"), new Chariot(Team.HAN));
        base.put(Position.from("12"), new Elephant(Team.HAN));
        base.put(Position.from("13"), new Horse(Team.HAN));
        base.put(Position.from("14"), new Guard(Team.HAN));
        base.put(Position.from("16"), new Guard(Team.HAN));
        base.put(Position.from("17"), new Horse(Team.HAN));
        base.put(Position.from("18"), new Elephant(Team.HAN));
        base.put(Position.from("19"), new Chariot(Team.HAN));
        base.put(Position.from("25"), new General(Team.HAN));
        base.put(Position.from("32"), new Cannon(Team.HAN));
        base.put(Position.from("38"), new Cannon(Team.HAN));
        base.put(Position.from("41"), new Soldier(Team.HAN));
        base.put(Position.from("43"), new Soldier(Team.HAN));
        base.put(Position.from("45"), new Soldier(Team.HAN));
        base.put(Position.from("47"), new Soldier(Team.HAN));
        base.put(Position.from("49"), new Soldier(Team.HAN));
    }

    private static void placeCho(Map<Position, Piece> base) {
        base.put(Position.from("01"), new Chariot(Team.CHO));
        base.put(Position.from("02"), new Elephant(Team.CHO));
        base.put(Position.from("03"), new Horse(Team.CHO));
        base.put(Position.from("04"), new Guard(Team.CHO));
        base.put(Position.from("06"), new Guard(Team.CHO));
        base.put(Position.from("07"), new Horse(Team.CHO));
        base.put(Position.from("08"), new Elephant(Team.CHO));
        base.put(Position.from("09"), new Chariot(Team.CHO));
        base.put(Position.from("95"), new General(Team.CHO));
        base.put(Position.from("82"), new Cannon(Team.CHO));
        base.put(Position.from("88"), new Cannon(Team.CHO));
        base.put(Position.from("71"), new Soldier(Team.CHO));
        base.put(Position.from("73"), new Soldier(Team.CHO));
        base.put(Position.from("75"), new Soldier(Team.CHO));
        base.put(Position.from("77"), new Soldier(Team.CHO));
        base.put(Position.from("79"), new Soldier(Team.CHO));
    }

    private static void applySetUp(Map<Position, Piece> base, PieceSetup hanSetup, PieceSetup choSetup) {
        hanSetup.apply(base, Team.HAN);
        choSetup.apply(base, Team.CHO);
    }
}
