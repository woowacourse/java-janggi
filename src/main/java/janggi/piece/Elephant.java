package janggi.piece;

import static janggi.moving.Movement.DOWN;
import static janggi.moving.Movement.LEFT;
import static janggi.moving.Movement.LEFT_DOWN;
import static janggi.moving.Movement.LEFT_UP;
import static janggi.moving.Movement.RIGHT;
import static janggi.moving.Movement.RIGHT_DOWN;
import static janggi.moving.Movement.RIGHT_UP;
import static janggi.moving.Movement.UP;

import janggi.PieceType;
import janggi.moving.Movements;
import janggi.moving.Path;
import janggi.moving.PossibleMovements;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;
import java.util.List;

public class Elephant extends Piece {
    private static final PossibleMovements possibleMovements = new PossibleMovements(
            List.of(new Movements(UP, LEFT_UP, LEFT_UP), new Movements(UP, RIGHT_UP, RIGHT_UP),
                    new Movements(RIGHT, RIGHT_UP, RIGHT_UP), new Movements(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
                    new Movements(DOWN, RIGHT_DOWN, RIGHT_DOWN), new Movements(DOWN, LEFT_DOWN, LEFT_DOWN),
                    new Movements(LEFT, LEFT_UP, LEFT_UP), new Movements(LEFT, LEFT_DOWN, LEFT_DOWN)));

    protected Elephant(Team team, PieceType pieceType) {
        super(team, pieceType);
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
    protected PossibleMovements getPossibleMovements(Board board, Position start) {
        return possibleMovements;
    }

    @Override
    public int getScore() {
        return 3;
    }
}
