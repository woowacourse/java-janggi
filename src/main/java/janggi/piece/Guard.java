package janggi.piece;

import static janggi.Movement.DOWN;
import static janggi.Movement.LEFT;
import static janggi.Movement.RIGHT;
import static janggi.Movement.UP;

import janggi.Movement;
import janggi.Path;
import janggi.Team;
import janggi.board.Board;
import janggi.board.Position;

import java.util.ArrayList;
import java.util.List;

public class Guard extends Piece {
    private static final String NAME = "사";
    private static final List<List<Movement>> paths;

    static {
        paths = new ArrayList<>();
        paths.add(List.of(UP));
        paths.add(List.of(LEFT));
        paths.add(List.of(RIGHT));
        paths.add(List.of(DOWN));
    }

    public Guard(Team team) {
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
    public boolean isGeneral() {
        return false;
    }

    @Override
    public boolean isSameType(Piece other) {
        return other instanceof Guard;
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
