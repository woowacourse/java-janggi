package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board initialize(

    ) {
        Map<Position, Piece> pieces = new HashMap<>();
        initializeBoard(pieces);
        return new Board(pieces);
    }

    private static void initializeBoard(final Map<Position, Piece> pieces) {
        Cannon.INITIAL_POSITION.forEach((team, positions) -> positions
            .forEach(position -> pieces.put(position, new Cannon(team))));

        Chariot.INITIAL_POSITION.forEach((team, positions) -> positions
            .forEach(position -> pieces.put(position, new Chariot(team))));

        Elephant.INITIAL_POSITION.forEach((team, positions) -> positions
            .forEach(position -> pieces.put(position, new Elephant(team))));

        General.INITIAL_POSITION.forEach((team, positions) -> positions
            .forEach(position -> pieces.put(position, new General(team))));

        Guard.INITIAL_POSITION.forEach((team, positions) -> positions
            .forEach(position -> pieces.put(position, new Guard(team))));

        Horse.INITIAL_POSITION.forEach((team, positions) -> positions
            .forEach(position -> pieces.put(position, new Horse(team))));

        쭈.INITIAL_POSITION.forEach((team, positions) -> positions
            .forEach(position -> pieces.put(position, new 쭈(team))));

    }
}
