package janggi.piece;

import static janggi.Movement.DOWN;
import static janggi.Movement.LEFT;
import static janggi.Movement.RIGHT;
import static janggi.Movement.UP;

import janggi.Movement;
import janggi.Team;
import janggi.board.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    protected void validatePath(Map<Position, Piece> board, List<Position> path) {
        int pieceCount = 0;
        path.removeFirst();
        path.removeLast();
        for (Position position : path) {
            Piece piece = board.get(position);
            if (piece == null) {
                continue;
            }
            if (piece.isSameType(this)) {
                throw new IllegalArgumentException("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
            }
            pieceCount++;
        }
        if (pieceCount != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 다른 기물 1개를 넘어가야 합니다.");
        }
    }

    @Override
    protected void validatePieceOnGoal(Map<Position, Piece> board, Position goal) {
        validateSameTeamOnGoal(board, goal);
        Piece other = board.get(goal);
        if (other != null && other.isSameType(this)) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 잡을 수 없습니다.");
        }
    }

    @Override
    public boolean isGeneral() {
        return false;
    }

    @Override
    protected boolean isSameType(Piece other) {
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
