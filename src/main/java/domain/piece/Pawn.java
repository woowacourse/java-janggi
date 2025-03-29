package domain.piece;

import domain.Moves;
import domain.Position;
import domain.Team;
import domain.movement.PawnMovement;
import java.util.List;

public class Pawn extends Piece {

    private static final int SCORE = 2;
    private static final PawnMovement movement = new PawnMovement();

    public Pawn(Team team, Position position) {
        super(team, position);
    }

    @Override
    public List<Moves> getMoveOptions(Position src, Position dest) {
        return movement.findPossibleMoves(team);
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}
