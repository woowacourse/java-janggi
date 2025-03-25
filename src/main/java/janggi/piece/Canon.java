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

public class Canon extends Piece {
    protected static final String NAME = "포";
    private static final PossibleMovements possibleMovements = new PossibleMovements(
            List.of(new Movements(UP_STRAIGHT), new Movements(DOWN_STRAIGHT), new Movements(LEFT_STRAIGHT),
                    new Movements(RIGHT_STRAIGHT)));

    public Canon(Team team) {
        super(team);
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
    protected Path calculatePath(Position start, Position goal) {
        return possibleMovements.calculatePath(start, goal);
    }

    @Override
    public boolean isCanon() {
        return true;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
