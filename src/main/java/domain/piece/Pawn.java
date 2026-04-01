package domain.piece;

import domain.Team;
import domain.strategy.MoveStrategy;


public class Pawn extends Piece {

    public Pawn(Team team, MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }
}
