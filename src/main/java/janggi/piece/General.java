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

public class General extends Piece {
    private static final String NAME = "궁";
    private static final List<List<Movement>> paths;

    static {
        paths = new ArrayList<>();
        paths.add(List.of(UP));
        paths.add(List.of(LEFT));
        paths.add(List.of(RIGHT));
        paths.add(List.of(DOWN));
    }

    public General(Team team) {
        super(team);
    }

    @Override
    protected void validatePath(Map<Position, Piece> board, List<Position> path) {
        validateNonPieceOnPath(board, path);
    }

    @Override
    protected void validatePieceOnGoal(Map<Position, Piece> board, Position goal) {
        validateSameTeamOnGoal(board, goal);
    }

    @Override
    public boolean isGeneral() {
        return true;
    }

    @Override
    protected boolean isSameType(Piece other) {
        return other instanceof General;
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
