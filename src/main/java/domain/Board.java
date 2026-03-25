package domain;

import domain.vo.Position;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    private Board() {
        board = new HashMap<>();
        board.put(Position.of(0, 0),Chariot.of(Team.CHU));
        board.put(Position.of(0, 1),Chariot.of(Team.CHU));
        board.put(Position.of(0, 2),Chariot.of(Team.CHU));
        board.put(Position.of(0, 3),Chariot.of(Team.CHU));
        board.put(Position.of(0, 4),Chariot.of(Team.CHU));
        board.put(Position.of(0, 6),Chariot.of(Team.CHU));
        board.put(Position.of(0, 7),Chariot.of(Team.CHU));
        board.put(Position.of(0, 8),Chariot.of(Team.CHU));
        board.put(Position.of(1, 4),Chariot.of(Team.CHU));
        board.put(Position.of(2, 1),Chariot.of(Team.CHU));
        board.put(Position.of(2, 8),Chariot.of(Team.CHU));
        board.put(Position.of(3, 0),Chariot.of(Team.CHU));
        board.put(Position.of(3, 2),Chariot.of(Team.CHU));
        board.put(Position.of(3, 4),Chariot.of(Team.CHU));
        board.put(Position.of(3, 6),Chariot.of(Team.CHU));
        board.put(Position.of(3, 8),Chariot.of(Team.CHU));

        board.put(Position.of(9, 0),Chariot.of(Team.CHU));
        board.put(Position.of(9, 1),Chariot.of(Team.CHU));
        board.put(Position.of(9, 2),Chariot.of(Team.CHU));
        board.put(Position.of(9, 3),Chariot.of(Team.CHU));
        board.put(Position.of(9, 4),Chariot.of(Team.CHU));
        board.put(Position.of(9, 6),Chariot.of(Team.CHU));
        board.put(Position.of(9, 7),Chariot.of(Team.CHU));
        board.put(Position.of(9, 8),Chariot.of(Team.CHU));
        board.put(Position.of(8, 4),Chariot.of(Team.CHU));
        board.put(Position.of(7, 1),Chariot.of(Team.CHU));
        board.put(Position.of(7, 8),Chariot.of(Team.CHU));
        board.put(Position.of(6, 0),Chariot.of(Team.CHU));
        board.put(Position.of(6, 2),Chariot.of(Team.CHU));
        board.put(Position.of(6, 4),Chariot.of(Team.CHU));
        board.put(Position.of(6, 6),Chariot.of(Team.CHU));
        board.put(Position.of(6, 8),Chariot.of(Team.CHU));
    }

    public static Board of() {
        return new Board();
    }
}
