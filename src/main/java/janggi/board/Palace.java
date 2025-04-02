package janggi.board;

import janggi.coordinate.Position;
import janggi.coordinate.Vector;
import janggi.player.Team;

import java.util.List;

public class Palace {

    private static final Position CHO_PALACE_CENTER = Position.of(9, 5);
    private static final Position HAN_PALACE_CENTER = Position.of(2, 5);

    private final Team team;
    private final List<Position> positions;

    private Palace(final Team team, final List<Position> positions) {
        this.team = team;
        this.positions = positions;
    }

    public static Palace from(final Team team) {
        final Position center = decideCenter(team);
        final Vector standard = Vector.create();
        return new Palace(
                team,
                List.of(center,
                        center.add(standard.up()),
                        center.add(standard.down()),
                        center.add(standard.left()),
                        center.add(standard.right()),
                        center.add(standard.up().left()),
                        center.add(standard.up().right()),
                        center.add(standard.down().left()),
                        center.add(standard.down().right())));
    }

    private static Position decideCenter(final Team team) {
        if (team.isCho()) {
            return CHO_PALACE_CENTER;
        }
        return HAN_PALACE_CENTER;
    }

    public boolean isPalace(final Position position) {
        return positions.contains(position);
    }

    public boolean isCenter(final Position position) {
        if (team == Team.CHO) {
            return position.equals(CHO_PALACE_CENTER);
        }
        return position.equals(HAN_PALACE_CENTER);
    }

    public boolean isSameTeam(final Team team) {
        return this.team == team;
    }
}
