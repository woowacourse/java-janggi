package domain;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board initialize() {
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(new Position(0, 0), new Chariot(Team.GREEN));
        pieces.put(new Position(1, 0), new Elephant(Team.GREEN));
        pieces.put(new Position(2, 0), new Horse(Team.GREEN));
        pieces.put(new Position(3, 0), new Guard(Team.GREEN));
        pieces.put(new Position(5, 0), new Guard(Team.GREEN));
        pieces.put(new Position(6, 0), new Horse(Team.GREEN));
        pieces.put(new Position(7, 0), new Elephant(Team.GREEN));
        pieces.put(new Position(8, 0), new Chariot(Team.GREEN));
        pieces.put(new Position(1, 2), new Cannon(Team.GREEN));
        pieces.put(new Position(7, 2), new Cannon(Team.GREEN));
        pieces.put(new Position(0, 3), new 쭈(Team.GREEN));
        pieces.put(new Position(2, 3), new 쭈(Team.GREEN));
        pieces.put(new Position(4, 3), new 쭈(Team.GREEN));
        pieces.put(new Position(6, 3), new 쭈(Team.GREEN));
        pieces.put(new Position(8, 3), new 쭈(Team.GREEN));
        pieces.put(new Position(4, 1), new General(Team.GREEN));

        pieces.put(new Position(0, 9), new Chariot(Team.RED));
        pieces.put(new Position(1, 9), new Elephant(Team.RED));
        pieces.put(new Position(2, 9), new Horse(Team.RED));
        pieces.put(new Position(3, 9), new Guard(Team.RED));
        pieces.put(new Position(5, 9), new Guard(Team.RED));
        pieces.put(new Position(6, 9), new Horse(Team.RED));
        pieces.put(new Position(7, 9), new Elephant(Team.RED));
        pieces.put(new Position(8, 9), new Chariot(Team.RED));
        pieces.put(new Position(1, 7), new Cannon(Team.RED));
        pieces.put(new Position(7, 7), new Cannon(Team.RED));
        pieces.put(new Position(0, 6), new 쭈(Team.RED));
        pieces.put(new Position(2, 6), new 쭈(Team.RED));
        pieces.put(new Position(4, 6), new 쭈(Team.RED));
        pieces.put(new Position(6, 6), new 쭈(Team.RED));
        pieces.put(new Position(8, 6), new 쭈(Team.RED));
        pieces.put(new Position(4, 8), new General(Team.RED));

        return new Board(pieces);
    }
}
