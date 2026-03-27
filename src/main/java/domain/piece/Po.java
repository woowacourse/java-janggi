package domain.piece;

import domain.player.Team;
import domain.rule.StraightPathGenerator;
import domain.strategy.PoMovementStrategy;

public class Po extends Piece {

    public Po(Team team) {
        super(team, PieceType.PO, new PoMovementStrategy(), new StraightPathGenerator());
    }

}
