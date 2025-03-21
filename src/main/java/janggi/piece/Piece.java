package janggi.piece;

import janggi.Movement;
import janggi.Team;
import janggi.board.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void validateMovable(Map<Position, Piece> board, Position start, Position goal) {
        List<Position> path = calculatePath(start, goal);
        validatePath(board, path);
        validatePieceOnGoal(board, goal);
    }

    private List<Position> calculatePath(Position start, Position goal) {
        List<List<Movement>> paths = getPaths();
        for (List<Movement> movements : paths) {
            List<Position> path = new ArrayList<>();
            Position position = start;
            path.add(start);
            for (Movement movement : movements) {
                position = movement.movePosition(position);
                path.add(position);
            }
            if (path.getLast().equals(goal)) {
                return path;
            }
        }
        throw new IllegalArgumentException("[ERROR] %s은/는 해당 목적지로 이동할 수 없습니다.".formatted(getName()));
    }

    protected void validateNonPieceOnPath(Map<Position, Piece> board, List<Position> path) {
        for (Position position : path) {
            if (path.getFirst() == position || path.getLast() == position) {
                continue;
            }
            boolean isPieceExists = board.containsKey(position);
            if (isPieceExists) {
                throw new IllegalArgumentException("[ERROR] 해당 경로에 다른 기물이 있어 이동할 수 없습니다.");
            }
        }
    }

    protected void validateSameTeamOnGoal(Map<Position, Piece> board, Position goal) {
        Piece other = board.get(goal);
        if (other == null) {
            return;
        }

        if (other.isSameTeam(team)) {
            throw new IllegalArgumentException("[ERROR] 목적지에 같은 진영의 기물이 있어 이동할 수 없습니다.");
        }
    }

    protected abstract void validatePath(Map<Position, Piece> board, List<Position> path);
    protected abstract boolean isSameType(Piece other);
    protected abstract void validatePieceOnGoal(Map<Position, Piece> board, Position goal);
    public abstract boolean isGeneral();
    protected abstract List<List<Movement>> getPaths();
    public abstract String getName();

    @Override
    public String toString() {
        return team.getName() + "나라 " + getName();
    }
}
