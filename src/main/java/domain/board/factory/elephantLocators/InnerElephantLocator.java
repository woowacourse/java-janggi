package domain.board.factory.elephantLocators;

import domain.Team;
import domain.board.Point;
import domain.pieces.Elephant;
import domain.pieces.Horse;
import domain.pieces.Piece;
import java.util.HashMap;
import java.util.Map;

public final class InnerElephantLocator implements ElephantLocator {

    @Override
    public Map<Point, Piece> setupElephant(final Team team) {
        final Map<Point, Piece> locations = new HashMap<>();
        locations.put(new Point(team.getInitialRow(), 2), new Elephant(team));
        locations.put(new Point(team.getInitialRow(), 6), new Elephant(team));
        return locations;
    }

    @Override
    public Map<Point, Piece> setupHorse(final Team team) {
        final Map<Point, Piece> locations = new HashMap<>();
        locations.put(new Point(team.getInitialRow(), 1), new Horse(team));
        locations.put(new Point(team.getInitialRow(), 7), new Horse(team));
        return locations;
    }
}
