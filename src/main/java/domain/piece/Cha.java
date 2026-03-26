package domain.piece;

import domain.player.Team;
import domain.rule.StraightPathGenerator;
import domain.strategy.BlockedMovementStrategy;

public class Cha extends Piece {

    public Cha(Team team) {
        super(team, PieceType.CHA, new BlockedMovementStrategy(), new StraightPathGenerator());
    }
}
