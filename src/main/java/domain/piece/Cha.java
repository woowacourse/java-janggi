package domain.piece;

import domain.direction.Direction;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.rule.StraightPathGenerator;
import domain.strategy.BlockedMovementStrategy;
import java.util.ArrayList;
import java.util.List;

public class Cha extends Piece {

    public Cha(Team team) {
        super(team, PieceType.CHA, new BlockedMovementStrategy(), new StraightPathGenerator());
    }

}
