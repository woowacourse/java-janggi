package domain.piece;

import domain.Moves;
import domain.Position;
import domain.Team;
import domain.movement.StraightMovement;
import java.util.List;

public class Cha extends Piece {

    private static final int SCORE = 13;
    private static final StraightMovement movement = new StraightMovement();

    public Cha(Team team, Position position) {
        super(team, position);
    }

    @Override
    public List<Moves> getMoveOptions(Position src, Position dest) {
        return List.of(movement.findPossibleMove(src, dest));
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}