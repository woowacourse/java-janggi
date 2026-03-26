package domain.piece;

import domain.direction.Direction;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.rule.StraightPathGenerator;
import domain.strategy.PoMovementStrategy;
import java.util.ArrayList;
import java.util.List;

public class Po extends Piece {

    public Po(Team team) {
        super(team, PieceType.PO, new PoMovementStrategy(), new StraightPathGenerator());
    }

}
