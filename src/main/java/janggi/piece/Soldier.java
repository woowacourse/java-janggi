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

public class Soldier extends Piece {
    private static final String NAME = "졸";
    // path 변수명을 다른 것으로 수정 TODO
    private static final List<List<Movement>> greenTeamPaths;
    private static final List<List<Movement>> redTeamPaths;

    static {
        greenTeamPaths = new ArrayList<>();
        greenTeamPaths.add(List.of(UP));
        greenTeamPaths.add(List.of(LEFT));
        greenTeamPaths.add(List.of(RIGHT));
        greenTeamPaths.add(List.of(DOWN));

        redTeamPaths = new ArrayList<>();
        redTeamPaths.add(List.of(UP));
        redTeamPaths.add(List.of(LEFT));
        redTeamPaths.add(List.of(RIGHT));
        redTeamPaths.add(List.of(DOWN));
    }

    public Soldier(Team team) {
        super(team);
    }

    @Override
    protected void validatePath(Board board, List<Position> path) {
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
        return other instanceof Soldier;
    }

    @Override
    protected List<List<Movement>> getPaths() {
        if (team == Team.RED) {
            return redTeamPaths;
        }
        return greenTeamPaths;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
