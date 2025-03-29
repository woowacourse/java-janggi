package janggi.piece;

import static janggi.Team.RED;
import static janggi.board.Board.*;
import static janggi.board.Board.GREEN_CASTLE;
import static janggi.board.Board.RED_CASTLE;
import static janggi.moving.Movement.DOWN;
import static janggi.moving.Movement.LEFT;
import static janggi.moving.Movement.RIGHT;
import static janggi.moving.Movement.UP;

import janggi.moving.Movements;
import janggi.moving.Path;
import janggi.moving.PossibleMovements;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;

import java.util.List;

public class General extends Piece {
    private static final String NAME = "궁";
    private static final PossibleMovements possibleMovements = new PossibleMovements(
            List.of(new Movements(UP), new Movements(LEFT), new Movements(RIGHT), new Movements(DOWN)));

    public General(Team team) {
        super(team);
    }

    @Override
    protected void validatePath(Board board, Path path) {
        validateOutOfCastle(path);
        validateInvalidDiagonalPath(path);
        validateNonPieceOnPath(board, path);
    }

    private void validateOutOfCastle(Path path) {
        List<Position> castle = GREEN_CASTLE;
        if (team == RED) {
            castle = RED_CASTLE;
        }
        for (Position position : path.getPath()) {
            if (!castle.contains(position)) {
                throw new IllegalArgumentException("[ERROR] 궁은 궁성을 벗어날 수 없습니다.");
            }
        }
    }

    private void validateInvalidDiagonalPath(Path path) {
        List<Position> castleBorder = CENTRAL_OF_GREEN_CASTLE_BORDER;
        if (team == RED) {
            castleBorder = CENTRAL_OF_RED_CASTLE_BORDER;
        }
        boolean isOneStep = path.isOneStep();
        boolean isFirstAndLastInCastleBorder = path.firstAndLastIn(castleBorder);
        if (isOneStep && isFirstAndLastInCastleBorder) {
            throw new IllegalArgumentException("[ERROR] 선이 존재하는 경우에만 이동할 수 있습니다.");
        }
    }

    @Override
    protected void validatePieceOnGoal(Board board, Position goal) {
        validateSameTeamOnGoal(board, goal);
    }

    @Override
    public boolean isGeneral() {
        return true;
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
