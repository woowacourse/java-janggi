package janggi.domain.palace;

import janggi.domain.board.Position;
import janggi.domain.piece.Team;

import java.util.Set;

public class PalaceFactory {

    public static Palace createPalace(Team team) {
        if (team == Team.HAN) {
            return createHanPalace();
        }
        return createChoPalace();
    }

    private static Palace createHanPalace() {
        Set<Position> positions = Set.of(
                new Position(4, 3), new Position(6, 3),
                new Position(5, 2),
                new Position(4, 1), new Position(6, 1));
        return new Palace(1, 3, positions);
    }

    private static Palace createChoPalace() {
        Set<Position> positions = Set.of(
                new Position(4, 10), new Position(6, 10),
                new Position(5, 9),
                new Position(4, 8), new Position(6, 8));
        return new Palace(8, 10, positions);
    }
}
