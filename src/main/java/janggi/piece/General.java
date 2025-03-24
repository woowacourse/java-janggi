package janggi.piece;

import static janggi.Movement.DOWN;
import static janggi.Movement.LEFT;
import static janggi.Movement.RIGHT;
import static janggi.Movement.UP;

import janggi.Movements;
import janggi.Path;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;

import java.util.List;

public class General extends Piece {
    private static final String NAME = "궁";
    private static final List<Movements> possibleMovements = List.of(new Movements(UP), new Movements(LEFT),
            new Movements(RIGHT), new Movements(DOWN));

    public General(Team team) {
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
        return true;
    }

    @Override
    protected List<Movements> getPossibleMovements() {
        return possibleMovements;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
