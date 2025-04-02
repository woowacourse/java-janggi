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
import janggi.moving.Movements;
import janggi.moving.Path;
import janggi.moving.PossibleMovements;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;
import java.util.List;

public class Canon extends Piece {
    private static final PossibleMovements POSSIBLE_MOVEMENTS = new PossibleMovements(
            List.of(new Movements(UP_STRAIGHT), new Movements(DOWN_STRAIGHT), new Movements(LEFT_STRAIGHT),
                    new Movements(RIGHT_STRAIGHT)));
    private static final PossibleMovements POSSIBLE_CASTLE_MOVEMENTS = new PossibleMovements(
            List.of(new Movements(UP_STRAIGHT), new Movements(DOWN_STRAIGHT), new Movements(LEFT_STRAIGHT),
                    new Movements(RIGHT_STRAIGHT), new Movements(RIGHT_UP), new Movements(RIGHT_UP, RIGHT_UP),
                    new Movements(LEFT_UP), new Movements(LEFT_UP, LEFT_UP), new Movements(RIGHT_DOWN),
                    new Movements(RIGHT_DOWN, RIGHT_DOWN), new Movements(LEFT_DOWN),
                    new Movements(LEFT_DOWN, LEFT_DOWN)));

    protected Canon(Team team, PieceType pieceType) {
        super(team, pieceType);
    }

    @Override
    protected void validatePath(Board board, Path path) {
        List<Position> intermediatePath = path.getIntermediatePath();
        validateCanonExists(board, intermediatePath);
        validateIntermediatePieceCount(board, intermediatePath);
    }

    private void validateCanonExists(Board board, List<Position> intermediatePath) {
        boolean isCanonExists = intermediatePath.stream()
                .anyMatch(board::isCanonExists);
        if (isCanonExists) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
        }
    }

    private void validateIntermediatePieceCount(Board board, List<Position> intermediatePath) {
        int pieceCount = (int) intermediatePath.stream()
                .filter(board::isPieceExists)
                .count();
        if (pieceCount != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 다른 기물 1개를 넘어가야 합니다.");
        }
    }

    @Override
    protected void validatePieceOnGoal(Board board, Position goal) {
        validateSameTeamOnGoal(board, goal);
        boolean isCanonOnGoal = board.isCanonExists(goal);
        if (isCanonOnGoal) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 잡을 수 없습니다.");
        }
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
        return 7;
    }
}
