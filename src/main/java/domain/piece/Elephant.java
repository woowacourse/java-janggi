package domain.piece;

import domain.Team;
import domain.strategy.ElephantStrategy;

public class Elephant extends MoveablePiece {

    public Elephant(Team team) {
        super(team, PieceType.ELEPHANT, new ElephantStrategy());
    }
}
