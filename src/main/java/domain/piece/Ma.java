package domain.piece;

import domain.Moves;
import domain.Position;
import domain.Team;
import domain.movement.MaMovement;
import java.util.List;

public class Ma extends Piece {

    private static final int SCORE = 5;
    private static final MaMovement movement = new MaMovement();

    public Ma(Team team, Position position) {
        super(team, position);
    }

    @Override
    public List<Moves> getMoveOptions(Position src, Position dest) {
        return movement.findPossibleMoves();
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}
