package janggi.piece;

import janggi.Position;
import janggi.Team;
import janggi.piece.strategy.BasicMovable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BoardFactory {

    public Board makeBoard() {
        Map<BasicMovable, Position> positions = new HashMap<>();
        makeHanPieces(positions);
        makeChoPieces(positions);

        Map<Position, BasicMovable> pieces = new HashMap<>();
        for (Entry<BasicMovable, Position> entry : positions.entrySet()) {
            pieces.put(entry.getValue(), entry.getKey());
        }
        return new Board(positions, pieces);
    }

    private static void makeChoPieces(Map<BasicMovable, Position> positions) {
        positions.put(new Chariot(Team.CHO), new Position(10, 1));
        positions.put(new Horse(Team.CHO), new Position(10, 2));
        positions.put(new Elephant(Team.CHO), new Position(10, 3));
        positions.put(new Guard(Team.CHO), new Position(10, 4));
        positions.put(new Guard(Team.CHO), new Position(10, 6));
        positions.put(new Elephant(Team.CHO), new Position(10, 7));
        positions.put(new Horse(Team.CHO), new Position(10, 8));
        positions.put(new Chariot(Team.CHO), new Position(10, 9));

        positions.put(new King(Team.CHO), new Position(9, 5));
        positions.put(new Cannon(Team.CHO), new Position(8, 2));
        positions.put(new Cannon(Team.CHO), new Position(8, 8));

        positions.put(new Jol(), new Position(7, 1));
        positions.put(new Jol(), new Position(7, 3));
        positions.put(new Jol(), new Position(7, 5));
        positions.put(new Jol(), new Position(7, 7));
        positions.put(new Jol(), new Position(7, 9));
    }

    private static void makeHanPieces(Map<BasicMovable, Position> positions) {
        positions.put(new Chariot(Team.HAN), new Position(1, 1));
        positions.put(new Horse(Team.HAN), new Position(1, 2));
        positions.put(new Elephant(Team.HAN), new Position(1, 3));
        positions.put(new Guard(Team.HAN), new Position(1, 4));
        positions.put(new Guard(Team.HAN), new Position(1, 6));
        positions.put(new Elephant(Team.HAN), new Position(1, 7));
        positions.put(new Horse(Team.HAN), new Position(1, 8));
        positions.put(new Chariot(Team.HAN), new Position(1, 9));

        positions.put(new King(Team.HAN), new Position(2, 5));
        positions.put(new Cannon(Team.HAN), new Position(3, 2));
        positions.put(new Cannon(Team.HAN), new Position(3, 8));

        positions.put(new Byeong(), new Position(4, 1));
        positions.put(new Byeong(), new Position(4, 3));
        positions.put(new Byeong(), new Position(4, 5));
        positions.put(new Byeong(), new Position(4, 7));
        positions.put(new Byeong(), new Position(4, 9));
    }
}
