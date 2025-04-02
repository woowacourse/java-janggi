package model.board;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import model.Position;
import model.Team;
import model.piece.Piece;

public class Palace {

    private static final Position CENTER_POSITION = new Position(4, 1);

    private List<Line> getMovableLinesForTeam(Team team) {
        Position center = initialPositionForTeam(team);
        return List.of(
            new Line(center, center.move(new Position(-1, -1))),
            new Line(center, center.move(new Position(-1, 1))),
            new Line(center, center.move(new Position(1, 1))),
            new Line(center, center.move(new Position(1, -1)))
        );
    }

    @Nullable
    public Piece.Route findMovableRouteInPalace(Team team, Position start, Position target) {
        if (getMovableLinesForTeam(team).contains(new Line(start, target))) {
            return new Piece.Route(List.of(target.difference(start)));
        }
        if (getMovableLinesForTeam(team).contains(new Line(target, start))) {
            return new Piece.Route(List.of(start.difference(target)));
        }
        return null;
    }

    private Position initialPositionForTeam(Team team) {
        return new Position(team.onBaseX(CENTER_POSITION.x()), team.onBaseY(CENTER_POSITION.y()));
    }

    public boolean isInPalace(Team team, Position position) {
        Position center = initialPositionForTeam(team);
        return position.x() >= center.x() - 1
            && position.x() <= center.x() + 1
            && position.y() >= center.y() - 1
            && position.y() <= center.y() + 1;
    }

    private record Line(
        Position position1,
        Position position2
    ) {

    }
}
