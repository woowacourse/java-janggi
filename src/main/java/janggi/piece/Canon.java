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

public class Canon extends Piece {
    protected static final String NAME = "포";
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

    public Canon(Team team) {
        super(team);
    }

    @Override
    protected void validatePath(Board board, Path path) {
        int pieceCount = 0;
        for (Position position : path.getIntermediatePath()) {
            boolean isPieceNotExists = board.isPieceNotExists(position);
            if (isPieceNotExists) {
                continue;
            }
            boolean isCanon = board.isSameTypePieceExists(position, this);
            if (isCanon) {
                throw new IllegalArgumentException("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
            }
            pieceCount++;
        }
        if (pieceCount != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 다른 기물 1개를 넘어가야 합니다.");
        }
    }

    @Override
    protected void validatePieceOnGoal(Board board, Position goal) {
        validateSameTeamOnGoal(board, goal);
        boolean isCanonOnGoal = board.isSameTypePieceExists(goal, this);
        if (isCanonOnGoal) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 잡을 수 없습니다.");
        }
    }

    @Override
    public boolean isGeneral() {
        return false;
    }

    @Override
    public boolean isSameType(Piece other) {
        return other instanceof Canon;
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
