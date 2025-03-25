package janggi.piece;

import static janggi.moving.Movement.DOWN_STRAIGHT;
import static janggi.moving.Movement.LEFT_STRAIGHT;
import static janggi.moving.Movement.RIGHT_STRAIGHT;
import static janggi.moving.Movement.UP_STRAIGHT;

import janggi.moving.Movements;
import janggi.moving.Path;
import janggi.moving.PossibleMovements;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;

import java.util.List;

public class Chariot extends Piece {
    protected static final String NAME = "차";
    private static final PossibleMovements possibleMovements = new PossibleMovements(
            List.of(new Movements(UP_STRAIGHT), new Movements(DOWN_STRAIGHT), new Movements(LEFT_STRAIGHT),
                    new Movements(RIGHT_STRAIGHT)));

    public Chariot(Team team) {
        super(team);
    }

    @Override
    protected void validatePath(Board board, Path path) {
        validateNonPieceOnPath(board, path);
    }

    @Override
    protected void validatePieceOnGoal(Board board, Position goal) {
        validateSameTeamOnGoal(board, goal);
    }

    @Override
    protected Path calculatePath(Position start, Position goal) {
        return possibleMovements.calculatePath(start, goal);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
