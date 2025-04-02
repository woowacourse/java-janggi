package model.piece;

import java.util.List;

import model.Position;
import model.Team;
import model.piece.movement.PaoMovement;

public class Pao extends Piece {

    public Pao(int x, int y, Team team) {
        super(x, y, team);
        routes.addAll(List.of(
            new Route(List.of(new Position(-1, 0))),
            new Route(List.of(new Position(0, 1))),
            new Route(List.of(new Position(1, 0))),
            new Route(List.of(new Position(0, -1)))
        ));
        movement.normalMoveDisable();
        movement.addMovement(new PaoMovement());
    }

    @Override
    public PieceType type() {
        return PieceType.PAO;
    }
}
