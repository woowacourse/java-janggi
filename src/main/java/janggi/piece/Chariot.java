package janggi.piece;

import static janggi.Movement.DOWN;
import static janggi.Movement.LEFT;
import static janggi.Movement.RIGHT;
import static janggi.Movement.UP;
import static java.util.Collections.unmodifiableList;

import janggi.Movement;
import janggi.Movements;
import janggi.Path;
import janggi.Team;
import janggi.board.Board;
import janggi.board.Position;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {
    protected static final String NAME = "차";
    private static final List<Movements> possibleMovements;

    static {
        List<Movements> allMovements = new ArrayList<>();
        for (Movement movement : List.of(UP, DOWN)) {
            for (int i = 1; i < 10; i++) {
                List<Movement> tempMoves = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    tempMoves.add(movement);
                }
                allMovements.add(new Movements(tempMoves));
            }
        }
        for (Movement movement : List.of(LEFT, RIGHT)) {
            for (int i = 1; i < 9; i++) {
                List<Movement> tempMoves = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    tempMoves.add(movement);
                }
                allMovements.add(new Movements(tempMoves));
            }
        }
        possibleMovements = allMovements;
    }

    public Chariot(Team team) {
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
        return other instanceof Chariot;
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
