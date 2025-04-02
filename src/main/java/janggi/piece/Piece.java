package janggi.piece;

import janggi.PieceType;
import janggi.moving.Path;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;
import janggi.moving.PossibleMovements;

public abstract class Piece {
    protected final Team team;
    protected final PieceType type;

    protected Piece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    public boolean isDifferentTeam(Team team) {
        return !isSameTeam(team);
    }

    public boolean isSameType(PieceType type) {
        return this.type == type;
    }

    public void validateMovable(Board board, Position start, Position goal) {
        PossibleMovements possibleMovements = getPossibleMovements(board, start);
        Path path = possibleMovements.calculatePath(start, goal);
        validateInvalidCastleDiagonalMove(board, start, goal, path);
        validatePath(board, path);
        validatePieceOnGoal(board, goal);
    }

    private void validateInvalidCastleDiagonalMove(Board board, Position start, Position goal, Path path) {
        boolean isStartPositionCentralOfCastleBoard = board.isCentralOfCastleBorder(start);
        boolean isGoalPositionCentralOfCastleBoard = board.isCentralOfCastleBorder(goal);
        boolean isPathOneStep = path.isOneStep();
        if (isStartPositionCentralOfCastleBoard && isGoalPositionCentralOfCastleBoard && isPathOneStep) {
            throw new IllegalArgumentException("[ERROR] 선이 존재하는 경우에만 대각으로 이동할 수 있습니다.");
        }
    }

    protected void validateSameTeamOnGoal(Board board, Position goal) {
        boolean isSameTeamExists = board.isSameTeamExists(goal, team);
        if (isSameTeamExists) {
            throw new IllegalArgumentException("[ERROR] 목적지에 같은 진영의 기물이 있어 이동할 수 없습니다.");
        }
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getType() {
        return type;
    }

    public String getDisplayName() {
        return type.getDisplayName();
    }

    protected void validateNonPieceOnPath(Board board, Path path) {
        for (Position position : path.getIntermediatePath()) {
            boolean isPieceExists = board.isPieceExists(position);
            if (isPieceExists) {
                throw new IllegalArgumentException("[ERROR] 해당 경로에 다른 기물이 있어 이동할 수 없습니다.");
            }
        }
    }

    protected abstract PossibleMovements getPossibleMovements(Board board, Position start);
    protected abstract void validatePath(Board board, Path path);
    protected abstract void validatePieceOnGoal(Board board, Position goal);
    public abstract int getScore();
}
