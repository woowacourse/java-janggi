package janggi.piece;

import janggi.Movement;
import janggi.Path;
import janggi.Team;
import janggi.board.Board;
import janggi.board.Position;

import janggi.board.PositionOutOfBoardBoundsException;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    protected final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    public boolean isDifferentTeam(Team team) {
        return this.team != team;
    }

    public void validateMovable(Board board, Position start, Position goal) {
        Path path = calculatePath(start, goal);
        validatePath(board, path);
        validatePieceOnGoal(board, goal);
    }

    private Path calculatePath(Position start, Position goal) {
        List<List<Movement>> paths = getPaths();
        for (List<Movement> movements : paths) {
            List<Position> path = new ArrayList<>();
            Position position = start;
            path.add(start);
            for (Movement movement : movements) {
                try {
                    position = movement.movePosition(position);
                } catch (PositionOutOfBoardBoundsException e) {
                    continue;
                }
                path.add(position);
            }
            if (position.equals(goal)) {
                return new Path(path);
            }
        }
        throw new IllegalArgumentException("[ERROR] %s은/는 해당 목적지로 이동할 수 없습니다.".formatted(getName()));
    }

    protected void validateNonPieceOnPath(Board board, Path path) {
        for (Position position : path.getIntermediatePath()) {
            boolean isPieceExists = board.isPieceExists(position);
            if (isPieceExists) {
                throw new IllegalArgumentException("[ERROR] 해당 경로에 다른 기물이 있어 이동할 수 없습니다.");
            }
        }
    }

    protected void validateSameTeamOnGoal(Board board, Position goal) {
        boolean isSameTeamExists = board.isSameTeamExists(goal, team);
        if (isSameTeamExists) {
            throw new IllegalArgumentException("[ERROR] 목적지에 같은 진영의 기물이 있어 이동할 수 없습니다.");
        }
    }

    @Override
    public String toString() {
        return team.getName() + "나라 " + getName();
    }

    protected abstract void validatePath(Board board, Path path);
    protected abstract void validatePieceOnGoal(Board board, Position goal);
    protected abstract List<List<Movement>> getPaths();
    public abstract boolean isSameType(Piece other);
    public abstract boolean isGeneral();
    public abstract String getName();
}
