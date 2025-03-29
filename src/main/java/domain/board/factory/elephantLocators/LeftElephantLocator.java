package domain.board.factory.elephantLocators;

import domain.board.Point;
import domain.pieces.Elephant;
import domain.pieces.Horse;
import domain.pieces.Piece;
import domain.player.Player;
import domain.player.Team;
import java.util.HashMap;
import java.util.Map;

public final class LeftElephantLocator implements ElephantLocator {

    @Override
    public Map<Point, Piece> setupElephant(final Player player) {
        final Map<Point, Piece> locations = new HashMap<>();
        final Team team = player.getTeam();
        locations.put(new Point(team.getInitialRow(), 1), new Elephant(player));
        locations.put(new Point(team.getInitialRow(), 6), new Elephant(player));
        return locations;
    }

    @Override
    public Map<Point, Piece> setupHorse(final Player player) {
        final Map<Point, Piece> locations = new HashMap<>();
        final Team team = player.getTeam();
        locations.put(new Point(team.getInitialRow(), 2), new Horse(player));
        locations.put(new Point(team.getInitialRow(), 7), new Horse(player));
        return locations;
    }
}
