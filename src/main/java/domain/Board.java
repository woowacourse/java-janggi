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
        initializeBoard(pieces);
        return new Board(pieces);
    }

    private static void initializeBoard(final Map<Position, Piece> pieces) {
        PieceType.CANNON.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.CANNON, team))));

        PieceType.CHARIOT.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.CHARIOT, team))));

        PieceType.ELEPHANT.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.ELEPHANT, team))));

        PieceType.GENERAL.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.GENERAL, team))));

        PieceType.GUARD.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.GUARD, team))));

        PieceType.HORSE.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.HORSE, team))));

        PieceType.쭈.getInitialPosition()
            .forEach((team, positions) -> positions
                .forEach(position -> pieces.put(position,
                    new Piece(PieceType.쭈, team))));
    }
}
