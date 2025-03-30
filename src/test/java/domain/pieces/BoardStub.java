package domain.pieces;

import domain.Team;
import domain.board.Board;
import domain.board.BoardPoint;
import java.util.HashMap;
import java.util.Map;

public class BoardStub {

    private static final int BOARD_ROW_MAX = 10;
    private static final int BOARD_COLUMN_MAX = 9;

    public static Board generateBoard() {
        final Map<BoardPoint, Piece> locations = new HashMap<>();
        locations.putAll(generateLocationsForHan());
        locations.putAll(generateLocationsForCho());
        return new Board(locations);
    }

    private static Map<BoardPoint, Piece> generateLocationsForHan() {
        final Map<BoardPoint, Piece> locations = new HashMap<>();
        locations.put(new BoardPoint(6, 0), new Soldier(Team.HAN));
        locations.put(new BoardPoint(6, 2), new Soldier(Team.HAN));
        locations.put(new BoardPoint(6, 4), new Soldier(Team.HAN));
        locations.put(new BoardPoint(6, 6), new Soldier(Team.HAN));
        locations.put(new BoardPoint(6, 8), new Soldier(Team.HAN));

        locations.put(new BoardPoint(9, 0), new Chariot(Team.HAN));
        locations.put(new BoardPoint(9, 8), new Chariot(Team.HAN));

        locations.put(new BoardPoint(7, 1), new Cannon(Team.HAN));
        locations.put(new BoardPoint(7, 7), new Cannon(Team.HAN));

        locations.put(new BoardPoint(9, 1), new Elephant(Team.HAN));
        locations.put(new BoardPoint(9, 7), new Elephant(Team.HAN));

        locations.put(new BoardPoint(9, 2), new Horse(Team.HAN));
        locations.put(new BoardPoint(9, 6), new Horse(Team.HAN));

        locations.put(new BoardPoint(8, 4), new General(Team.HAN));
        locations.put(new BoardPoint(9, 3), new Guard(Team.HAN));
        locations.put(new BoardPoint(9, 5), new Guard(Team.HAN));

        return locations;
    }

    private static Map<BoardPoint, Piece> generateLocationsForCho() {
        final Map<BoardPoint, Piece> locations = new HashMap<>();

        locations.put(new BoardPoint(3, 0), new Soldier(Team.CHO));
        locations.put(new BoardPoint(3, 2), new Soldier(Team.CHO));
        locations.put(new BoardPoint(3, 4), new Soldier(Team.CHO));
        locations.put(new BoardPoint(3, 6), new Soldier(Team.CHO));
        locations.put(new BoardPoint(3, 8), new Soldier(Team.CHO));

        locations.put(new BoardPoint(0, 0), new Chariot(Team.CHO));
        locations.put(new BoardPoint(0, 8), new Chariot(Team.CHO));

        locations.put(new BoardPoint(2, 1), new Cannon(Team.CHO));
        locations.put(new BoardPoint(2, 7), new Cannon(Team.CHO));

        locations.put(new BoardPoint(0, 1), new Elephant(Team.CHO));
        locations.put(new BoardPoint(0, 7), new Elephant(Team.CHO));

        locations.put(new BoardPoint(0, 2), new Horse(Team.CHO));
        locations.put(new BoardPoint(0, 6), new Horse(Team.CHO));

        locations.put(new BoardPoint(1, 4), new General(Team.CHO));
        locations.put(new BoardPoint(0, 3), new Guard(Team.CHO));
        locations.put(new BoardPoint(0, 5), new Guard(Team.CHO));

        return locations;
    }
}
