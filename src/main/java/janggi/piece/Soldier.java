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

public class Soldier extends Piece {
    private static final String NAME = "졸";
    private static final List<Movements> greenTeamPossibleMovements;
    private static final List<Movements> redTeamPossibleMovements;

    static {
        greenTeamPossibleMovements = new ArrayList<>();
        greenTeamPossibleMovements.add(new Movements(UP));
        greenTeamPossibleMovements.add(new Movements(LEFT));
        greenTeamPossibleMovements.add(new Movements(RIGHT));
        greenTeamPossibleMovements.add(new Movements(DOWN));

        redTeamPossibleMovements = new ArrayList<>();
        redTeamPossibleMovements.add(new Movements(UP));
        redTeamPossibleMovements.add(new Movements(LEFT));
        redTeamPossibleMovements.add(new Movements(RIGHT));
        redTeamPossibleMovements.add(new Movements(DOWN));
    }

    public Soldier(Team team) {
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
        return other instanceof Soldier;
    }

    @Override
    protected List<Movements> getPossibleMovements() {
        if (team == Team.RED) {
            return unmodifiableList(redTeamPossibleMovements);
        }
        return unmodifiableList(greenTeamPossibleMovements);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
