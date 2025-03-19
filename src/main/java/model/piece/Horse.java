package model.piece;

import java.util.List;

import model.Position;
import model.Team;

public class Horse extends Piece {

    public Horse(int x, int y, Team team) {
        super(x, y, team);
        routes.addAll(List.of(
            new Route(List.of(new Position(0, 1), new Position(1, 1))),
            new Route(List.of(new Position(0, 1), new Position(-1, 1))),

            new Route(List.of(new Position(0, -1), new Position(-1, -1))),
            new Route(List.of(new Position(0, -1), new Position(1, -1))),

            new Route(List.of(new Position(-1, 0), new Position(-1, 1))),
            new Route(List.of(new Position(-1, 0), new Position(-1, -1))),

            new Route(List.of(new Position(1, 0), new Position(1, 1))),
            new Route(List.of(new Position(1, 0), new Position(1, -1)))
        ));
    }
}
