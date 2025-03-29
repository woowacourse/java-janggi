package domain.piece;

import domain.Moves;
import domain.Position;
import domain.Team;
import domain.movement.PalaceMovement;
import java.util.List;

public class Sa extends Piece {

    private static final int SCORE = 3;
    private static final PalaceMovement movement = new PalaceMovement();

    public Sa(Team team, Position position) {
        super(team, position);
    }

    @Override
    public List<Moves> getMoveOptions(Position src, Position dest) {
        return movement.calculatePath(src);
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}
