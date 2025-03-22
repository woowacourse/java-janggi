package janggi.piece;

import static janggi.Movement.DOWN;
import static janggi.Movement.LEFT;
import static janggi.Movement.LEFT_DOWN;
import static janggi.Movement.LEFT_UP;
import static janggi.Movement.RIGHT;
import static janggi.Movement.RIGHT_DOWN;
import static janggi.Movement.RIGHT_UP;
import static janggi.Movement.UP;
import static java.util.Collections.unmodifiableList;

import janggi.Movements;
import janggi.Path;
import janggi.Team;
import janggi.board.Board;
import janggi.board.Position;
import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {
    private static final String NAME = "마";
    private static final List<Movements> possibleMovements;

    static {
        List<Movements> allMovements = new ArrayList<>();
        allMovements.add(new Movements(UP, LEFT_UP));
        allMovements.add(new Movements(UP, RIGHT_UP));
        allMovements.add(new Movements(RIGHT, RIGHT_UP));
        allMovements.add(new Movements(RIGHT, RIGHT_DOWN));
        allMovements.add(new Movements(DOWN, RIGHT_DOWN));
        allMovements.add(new Movements(DOWN, LEFT_DOWN));
        allMovements.add(new Movements(LEFT, LEFT_UP));
        allMovements.add(new Movements(LEFT, LEFT_DOWN));
        possibleMovements = allMovements;
    }

    public Horse(Team team) {
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
        return other instanceof Horse;
    }

    @Override
    protected List<Movements> getPossibleMovements() {
        return unmodifiableList(possibleMovements);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
