package janggi.piece;

import static janggi.Team.RED;
import static janggi.board.Board.GREEN_CASTLE;
import static janggi.board.Board.RED_CASTLE;
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

public class Guard extends Piece {
    private static final PossibleMovements possibleMovements = new PossibleMovements(
            List.of(new Movements(UP), new Movements(LEFT), new Movements(RIGHT), new Movements(DOWN),
                    new Movements(LEFT_UP), new Movements(LEFT_DOWN), new Movements(RIGHT_UP),
                    new Movements(RIGHT_DOWN)));

    protected Guard(Team team, PieceType pieceType) {
        super(team, pieceType);
    }

    @Override
    protected void validatePath(Board board, Path path) {
        validateOutOfCastle(path);
        validateNonPieceOnPath(board, path);
    }

    private void validateOutOfCastle(Path path) {
        List<Position> castle = GREEN_CASTLE;
        if (team == RED) {
            castle = RED_CASTLE;
        }
        for (Position position : path.getPath()) {
            if (!castle.contains(position)) {
                throw new IllegalArgumentException("[ERROR] 사는 궁성을 벗어날 수 없습니다.");
            }
        }
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
