package janggi.piece;

import static janggi.Movement.DOWN;
import static janggi.Movement.LEFT;
import static janggi.Movement.RIGHT;
import static janggi.Movement.UP;
import static java.util.Collections.unmodifiableList;

import janggi.Movements;
import janggi.Path;
import janggi.Team;
import janggi.board.Board;
import janggi.board.Position;

import java.util.ArrayList;
import java.util.List;

public class Guard extends Piece {
    private static final String NAME = "사";
    private static final List<Movements> possibleMovements;

    static {
        List<Movements> allMovements = new ArrayList<>();
        allMovements.add(new Movements(UP));
        allMovements.add(new Movements(LEFT));
        allMovements.add(new Movements(RIGHT));
        allMovements.add(new Movements(DOWN));
        possibleMovements = allMovements;
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
    protected List<Movements> getPossibleMovements() {
        return unmodifiableList(possibleMovements);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
