package janggi.piece;

import janggi.moving.Path;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;

public abstract class Piece {
    protected final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    public boolean isDifferentTeam(Team team) {
        return !isSameTeam(team);
    }

    public void validateMovable(Board board, Position start, Position goal) {
        Path path = calculatePath(start, goal);
        validatePath(board, path);
        validatePieceOnGoal(board, goal);
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

    public boolean isGeneral() {
        return false;
    }

    public boolean isCanon() {
        return false;
    }


    protected abstract Path calculatePath(Position start, Position goal);
    protected abstract void validatePath(Board board, Path path);
    protected abstract void validatePieceOnGoal(Board board, Position goal);
    public abstract String getName();
}
