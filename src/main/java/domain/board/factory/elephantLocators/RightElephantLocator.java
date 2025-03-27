package domain.board.factory.elephantLocators;

import domain.board.Point;
import domain.pieces.Elephant;
import domain.pieces.Horse;
import domain.pieces.Piece;
import domain.player.TeamType;
import java.util.HashMap;
import java.util.Map;

public final class RightElephantLocator implements ElephantLocator {

    @Override
    public Map<Point, Piece> setupElephant(final TeamType teamType) {
        final Map<Point, Piece> locations = new HashMap<>();
        locations.put(new Point(teamType.getInitialRow(), 2), new Elephant(teamType));
        locations.put(new Point(teamType.getInitialRow(), 7), new Elephant(teamType));
        return locations;
    }

    @Override
    public Map<Point, Piece> setupHorse(final TeamType teamType) {
        final Map<Point, Piece> locations = new HashMap<>();
        locations.put(new Point(teamType.getInitialRow(), 1), new Horse(teamType));
        locations.put(new Point(teamType.getInitialRow(), 6), new Horse(teamType));
        return locations;
    }
}
