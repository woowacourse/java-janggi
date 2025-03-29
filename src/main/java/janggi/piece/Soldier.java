package janggi.piece;

import static janggi.moving.Movement.DOWN;
import static janggi.moving.Movement.LEFT;
import static janggi.moving.Movement.LEFT_DOWN;
import static janggi.moving.Movement.LEFT_UP;
import static janggi.moving.Movement.RIGHT;
import static janggi.moving.Movement.RIGHT_DOWN;
import static janggi.moving.Movement.RIGHT_UP;
import static janggi.moving.Movement.UP;

import janggi.moving.Movements;
import janggi.moving.Path;
import janggi.moving.PossibleMovements;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;
import java.util.List;

public class Soldier extends Piece {
    private static final String NAME = "졸";
    private static final PossibleMovements GREEN_POSSIBLE_MOVEMENTS = new PossibleMovements(List.of(new Movements(UP),
            new Movements(LEFT), new Movements(RIGHT), new Movements(RIGHT_UP), new Movements(LEFT_UP)));
    private static final PossibleMovements RED_POSSIBLE_MOVEMENTS = new PossibleMovements(List.of(new Movements(LEFT),
            new Movements(RIGHT), new Movements(DOWN), new Movements(RIGHT_DOWN), new Movements(LEFT_DOWN)));

    public Soldier(Team team) {
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
        if (team == Team.RED) {
            return RED_POSSIBLE_MOVEMENTS.calculatePath(start, goal);
        }
        return GREEN_POSSIBLE_MOVEMENTS.calculatePath(start, goal);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
