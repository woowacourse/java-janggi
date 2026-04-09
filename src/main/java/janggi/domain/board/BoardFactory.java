package janggi.domain.board;

import janggi.domain.piece.*;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.domain.team.Team;
import janggi.repository.PieceInfo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public static Board restore(List<PieceInfo> pieces) {
        Map<Position, Piece> base = new LinkedHashMap<>();
        initializeEmpty(base);
        for (PieceInfo pieceInfo : pieces) {
            Position position = Position.of(pieceInfo.getRowValue(), pieceInfo.getColumnValue());
            Team team = Team.valueOf(pieceInfo.getTeam());
            Piece piece = PieceType.valueOf(pieceInfo.getPieceType()).createPiece(team);
            base.put(position, piece);
        }
        return new Board(base);
    }

    public static Board restore(Map<Position, Piece> pieces) {
        Map<Position, Piece> base = new LinkedHashMap<>();
        initializeEmpty(base);
        base.putAll(pieces);

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
        base.put(Position.of(1, 1), new Chariot(Team.HAN));
        base.put(Position.of(1, 2), new Elephant(Team.HAN));
        base.put(Position.of(1, 3), new Horse(Team.HAN));
        base.put(Position.of(1, 4), new Guard(Team.HAN));
        base.put(Position.of(1, 6), new Guard(Team.HAN));
        base.put(Position.of(1, 7), new Horse(Team.HAN));
        base.put(Position.of(1, 8), new Elephant(Team.HAN));
        base.put(Position.of(1, 9), new Chariot(Team.HAN));
        base.put(Position.of(2, 5), new General(Team.HAN));
        base.put(Position.of(3, 2), new Cannon(Team.HAN));
        base.put(Position.of(3, 8), new Cannon(Team.HAN));
        base.put(Position.of(4, 1), new Soldier(Team.HAN));
        base.put(Position.of(4, 3), new Soldier(Team.HAN));
        base.put(Position.of(4, 5), new Soldier(Team.HAN));
        base.put(Position.of(4, 7), new Soldier(Team.HAN));
        base.put(Position.of(4, 9), new Soldier(Team.HAN));
    }

    private static void placeCho(Map<Position, Piece> base) {
        base.put(Position.of(10, 1), new Chariot(Team.CHO));
        base.put(Position.of(10, 2), new Elephant(Team.CHO));
        base.put(Position.of(10, 3), new Horse(Team.CHO));
        base.put(Position.of(10, 4), new Guard(Team.CHO));
        base.put(Position.of(10, 6), new Guard(Team.CHO));
        base.put(Position.of(10, 7), new Horse(Team.CHO));
        base.put(Position.of(10, 8), new Elephant(Team.CHO));
        base.put(Position.of(10, 9), new Chariot(Team.CHO));
        base.put(Position.of(9, 5), new General(Team.CHO));
        base.put(Position.of(8, 2), new Cannon(Team.CHO));
        base.put(Position.of(8, 8), new Cannon(Team.CHO));
        base.put(Position.of(7, 1), new Soldier(Team.CHO));
        base.put(Position.of(7, 3), new Soldier(Team.CHO));
        base.put(Position.of(7, 5), new Soldier(Team.CHO));
        base.put(Position.of(7, 7), new Soldier(Team.CHO));
        base.put(Position.of(7, 9), new Soldier(Team.CHO));
    }

    private static void applySetUp(Map<Position, Piece> base, PieceSetup hanSetup, PieceSetup choSetup) {
        hanSetup.apply(base, Team.HAN);
        choSetup.apply(base, Team.CHO);
    }
}
