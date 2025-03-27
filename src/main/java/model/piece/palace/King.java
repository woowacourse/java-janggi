package model.piece.palace;

import java.util.List;

import model.Position;
import model.Team;
import model.piece.PieceType;

public class King extends PalacePiece {

    public King(int x, int y, Team team) {
        super(x, y, team);
        routes.addAll(List.of(
            new Route(List.of(new Position(-1, 0))),
            new Route(List.of(new Position(0, 1))),
            new Route(List.of(new Position(1, 0))),
            new Route(List.of(new Position(0, -1)))
        ));
    }

    @Override
    public PieceType type() {
        return PieceType.PALACE;
    }
}
