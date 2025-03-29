package domain.piece;

import domain.Moves;
import domain.Position;
import domain.Team;
import domain.movement.SangMovement;
import java.util.List;

public class Sang extends Piece {

    private static final int SCORE = 3;
    private static final SangMovement movement = new SangMovement();

    public Sang(Team team, Position position) {
        super(team, position);
    }

    @Override
    public List<Moves> getMoveOptions(Position startPosition, Position targetPosition) {
        return movement.findPossibleMoves();

    }

    @Override
    public int getScore() {
        return SCORE;
    }
}
