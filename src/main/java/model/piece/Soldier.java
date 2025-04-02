package model.piece;

import java.util.List;

import model.Position;
import model.Team;
import model.piece.movement.PalaceMovement;

public class Soldier extends Piece {

    public Soldier(int x, int y, Team team) {
        super(x, y, team);
        routes.addAll(List.of(
            new Route(List.of(new Position(-1, 0))),
            new Route(List.of(new Position(0, 1))),
            new Route(List.of(new Position(1, 0))),
            new Route(List.of(new Position(0, -1)))
        ));
        movement.addMovement(new PalaceMovement(false, false));
    }

    @Override
    public PieceType type() {
        return PieceType.SOLDIER;
    }
}
