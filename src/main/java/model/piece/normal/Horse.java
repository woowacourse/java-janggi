package model.piece.normal;

import java.util.List;

import model.Position;
import model.Team;
import model.piece.PieceType;

public class Horse extends NormalPiece {

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

    @Override
    public PieceType type() {
        return PieceType.HORSE;
    }
}
