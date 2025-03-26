package board;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import piece.Cannon;
import piece.Chariot;
import piece.Elephant;
import piece.Guard;
import piece.Horse;
import piece.King;
import piece.Piece;
import piece.Soldier;
import piece.Team;

public class BoardInitializer {

    public Map<Position, Piece> init() {
        Map<Position, Piece> pieces = new HashMap<>();
        Arrays.stream(Team.values()).forEach(team -> {
            putSoldiers(team, pieces);
            putCannon(team, pieces);
            putKing(team, pieces);
            putChariot(team, pieces);
            putGuard(team, pieces);
            putElephant(team, pieces);
            putHorse(team, pieces);
        });
        return pieces;
    }

    private void putHorse(final Team team, final Map<Position, Piece> pieces) {
        pieces.put(new Position(calculateRow(team, 0), 3), new Horse(team));
        pieces.put(new Position(calculateRow(team, 0), 8), new Horse(team));
    }

    private void putElephant(final Team team, final Map<Position, Piece> pieces) {
        pieces.put(new Position(calculateRow(team, 0), 2), new Elephant(team));
        pieces.put(new Position(calculateRow(team, 0), 7), new Elephant(team));
    }

    private void putGuard(final Team team, final Map<Position, Piece> pieces) {
        pieces.put(new Position(calculateRow(team, 0), 4), new Guard(team));
        pieces.put(new Position(calculateRow(team, 0), 6), new Guard(team));
    }

    private void putChariot(final Team team, final Map<Position, Piece> pieces) {
        pieces.put(new Position(calculateRow(team, 0), 1), new Chariot(team));
        pieces.put(new Position(calculateRow(team, 0), 9), new Chariot(team));
    }

    private void putKing(final Team team, final Map<Position, Piece> pieces) {
        pieces.put(new Position(calculateRow(team, 1), 5), new King(team));
        pieces.put(new Position(calculateRow(team, 1), 5), new King(team));
    }

    private void putCannon(final Team team, final Map<Position, Piece> pieces) {
        pieces.put(new Position(calculateRow(team, 2), 2), new Cannon(team));
        pieces.put(new Position(calculateRow(team, 2), 8), new Cannon(team));

    }

    private void putSoldiers(final Team team, final Map<Position, Piece> pieces) {
        IntStream.range(1, 10)
                .forEach(column -> {
                    if (isSoldierColumn(column)) {
                        pieces.put(new Position(calculateRow(team, 3), column), new Soldier(team));
                    }
                });
    }

    private int calculateRow(final Team team, final int rankLine) {
        return team.getInitRow() + team.convertRowOffsetByTeam(rankLine);
    }

    private static boolean isSoldierColumn(final int column) {
        return column % 2 != 0;
    }

}
