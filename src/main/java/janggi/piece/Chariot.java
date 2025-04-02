package janggi.piece;

import static janggi.moving.Movement.DOWN_STRAIGHT;
import static janggi.moving.Movement.LEFT_DOWN;
import static janggi.moving.Movement.LEFT_STRAIGHT;
import static janggi.moving.Movement.LEFT_UP;
import static janggi.moving.Movement.RIGHT_DOWN;
import static janggi.moving.Movement.RIGHT_STRAIGHT;
import static janggi.moving.Movement.RIGHT_UP;
import static janggi.moving.Movement.UP_STRAIGHT;

import janggi.PieceType;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;
import janggi.moving.Movements;
import janggi.moving.Path;
import janggi.moving.PossibleMovements;
import java.util.List;

public class Chariot extends Piece {
    private static final PossibleMovements POSSIBLE_MOVEMENTS = new PossibleMovements(
            List.of(new Movements(UP_STRAIGHT), new Movements(DOWN_STRAIGHT), new Movements(LEFT_STRAIGHT),
                    new Movements(RIGHT_STRAIGHT)));
    private static final PossibleMovements POSSIBLE_CASTLE_MOVEMENTS = new PossibleMovements(
            List.of(new Movements(UP_STRAIGHT), new Movements(DOWN_STRAIGHT), new Movements(LEFT_STRAIGHT),
                    new Movements(RIGHT_STRAIGHT), new Movements(RIGHT_UP), new Movements(RIGHT_UP, RIGHT_UP),
                    new Movements(LEFT_UP), new Movements(LEFT_UP, LEFT_UP), new Movements(RIGHT_DOWN),
                    new Movements(RIGHT_DOWN, RIGHT_DOWN), new Movements(LEFT_DOWN),
                    new Movements(LEFT_DOWN, LEFT_DOWN)));

    protected Chariot(Team team, PieceType pieceType) {
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
        if (board.isInCastle(start)) {
            return POSSIBLE_CASTLE_MOVEMENTS;
        }
        return POSSIBLE_MOVEMENTS;
    }

    @Override
    public int getScore() {
        return 13;
    }
}
