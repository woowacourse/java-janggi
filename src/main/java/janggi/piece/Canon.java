package janggi.piece;

import static janggi.Movement.DOWN;
import static janggi.Movement.LEFT;
import static janggi.Movement.RIGHT;
import static janggi.Movement.UP;

import janggi.Movement;
import janggi.Team;
import janggi.board.Board;
import janggi.board.Position;
import java.util.ArrayList;
import java.util.List;

public class Canon extends Piece {
    protected static final String NAME = "포";
    private static final List<List<Movement>> paths;

    static {
        paths = new ArrayList<>();
        for (Movement movement : List.of(UP, DOWN)) {
            for (int i = 1; i < 10; i++) {
                List<Movement> tempMoves = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    tempMoves.add(movement);
                }
                paths.add(tempMoves);
            }
        }
        for (Movement movement : List.of(LEFT, RIGHT)) {
            for (int i = 1; i < 9; i++) {
                List<Movement> tempMoves = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    tempMoves.add(movement);
                }
                paths.add(tempMoves);
            }
        }
    }

    public Canon(Team team) {
        super(team);
    }

    @Override
    protected void validatePath(Board board, List<Position> path) {
        int pieceCount = 0;
        path.removeFirst();
        path.removeLast();
        for (Position position : path) {
            boolean isPieceNotExists = board.isPieceNotExists(position);
            if (isPieceNotExists) {
                continue;
            }
            boolean isCanon = board.isSameTypePieceExists(position, this);
            if (isCanon) {
                throw new IllegalArgumentException("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
            }
            pieceCount++;
        }
        if (pieceCount != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 다른 기물 1개를 넘어가야 합니다.");
        }
    }

    @Override
    protected void validatePieceOnGoal(Board board, Position goal) {
        validateSameTeamOnGoal(board, goal);
        boolean isCanonOnGoal = board.isSameTypePieceExists(goal, this);
        if (isCanonOnGoal) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 잡을 수 없습니다.");
        }
    }

    @Override
    public boolean isGeneral() {
        return false;
    }

    @Override
    public boolean isSameType(Piece other) {
        return other instanceof Canon;
    }

    @Override
    protected List<List<Movement>> getPaths() {
        return paths;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
