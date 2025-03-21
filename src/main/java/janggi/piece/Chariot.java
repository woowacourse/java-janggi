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

public class Chariot extends Piece {
    protected static final String NAME = "차";
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

    public Chariot(Team team) {
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
        return false;
    }

    @Override
    protected boolean isSameType(Piece other) {
        return other instanceof Chariot;
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
