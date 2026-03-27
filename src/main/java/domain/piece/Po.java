package domain.piece;

import domain.pathgenerator.StraightPathGenerator;
import domain.player.Team;
import domain.strategy.PoMovementStrategy;

public class Po extends Piece {

    public Po(Team team) {
        super(team, PieceType.PO, new PoMovementStrategy(), new StraightPathGenerator());
    }

}
