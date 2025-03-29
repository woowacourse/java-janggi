package janggi.piece;

import janggi.board.Board;
import janggi.board.Position;

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

    public void validateMovable(Board board, Position start, Position goal) {
        Piece attacker = board.getPiece(start);
        validateNonOurArmyAtGoal(board, goal, attacker.getTeam());
        validatePath(board, start, goal);
    }

    public Team getTeam() {
        return team;
    }

    protected void validatePath(Board board, Position start, Position goal) {
        Piece attacker = board.getPiece(start);
        List<Position> positionsOnPath = attacker.findPositionsInPath(start, goal);

        for (Position position : positionsOnPath) {
            validateNonPieceInPosition(board, position);
        }
    }

    private void validateNonPieceInPosition(Board board, Position position) {
        if (board.existPiece(position)) {
            throw new IllegalArgumentException("[ERROR] 이동 경로 중 특정 위치에 다른 기물이 있어 해당 기물을 목적지로 이동할 수 없습니다.");
        }
    }

    protected void validateNonOurArmyAtGoal(Board board, Position goal, Team attackerTeam) {
        // 목적지에 아군이 존재하면 예외 발생
        Piece target = board.getPiece(goal);
        if (target != null && target.isSameTeam(attackerTeam)) {
            throw new IllegalArgumentException("[ERROR] 목적지에 같은 진영의 기물이 있어 이동할 수 없습니다.");
        }
    }

    protected List<Position> findPositionsInPath(Position start, Position goal) {
        // 경로 안에 모든 좌표 탐색
        List<Position> innerPositions = new ArrayList<>();
        Position current = start;

        while (!current.equals(goal)) {
            current = determineNextPosition(goal, current);
            innerPositions.add(current);
        }

        removeGoalInPath(innerPositions);
        return innerPositions;
    }

    private Position determineNextPosition(Position goal, Position current) {
        int columnDifference = current.calculatesColumnDifference(goal);
        int rowDifference = current.calculatesRowDifference(goal);

        int columnStep = (int) Math.signum(columnDifference);
        int rowStep = (int) Math.signum(rowDifference);

        if (Math.abs(columnDifference) > Math.abs(rowDifference)) {
            current = current.modify(columnStep, 0);
        }
        if (Math.abs(columnDifference) < Math.abs(rowDifference)) {
            current = current.modify(0, rowStep);
        }

        if (Math.abs(columnDifference) == Math.abs(rowDifference)) {
            current = current.modify(columnStep, rowStep);
        }

        return current;
    }

    private void removeGoalInPath(List<Position> innerPositions) {
        innerPositions.removeLast();
    }

    @Override
    public String toString() {
        return getName();
    }

    protected abstract String getName();

    public abstract boolean isSameType(PieceType pieceType);

    public abstract PieceType getType();
}
