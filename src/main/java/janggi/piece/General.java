package janggi.piece;

import janggi.board.Board;
import janggi.board.Position;

import java.util.List;

public class General extends Piece {
    private final PieceType pieceType;

    public General(Team team) {
        super(team);
        this.pieceType = PieceType.GENERAL;
    }

    @Override
    public void validateMovable(Board board, Position start, Position goal) {
        validateMoveArea(board, start, goal);
        Piece attacker = board.getPiece(start);
        validateNonOurArmyAtGoal(board, goal, attacker.getTeam());
        validatePath(board, start, goal);
    }

    public void validateMoveArea(Board board, Position start, Position goal) {
        Piece general = board.getPiece(start);
        if (general.getTeam() == Team.GREEN) {
            validateGreenStart(board, start);
            validateGreenGoal(board, goal);
        }

        if (general.getTeam() == Team.RED) {
            validateRedStart(board, start);
            validateRedGoal(board, goal);
        }
    }

    private void validateGreenStart(Board board, Position start) {
        if (!board.isInnerBottomPalace(start)) {
            throw new IllegalArgumentException("[ERROR] 초나라 장의 출발 지점은 하단부 궁성 내 좌표여야 합니다.");
        }
    }

    private void validateGreenGoal(Board board, Position goal) {
        if (!board.isInnerBottomPalace(goal)) {
            throw new IllegalArgumentException("[ERROR] 초나라 장의 목적 지점은 하단부 궁성 내 좌표여야 합니다.");
        }
    }

    private void validateRedStart(Board board, Position start) {
        if (!board.isInnerUpperPalace(start)) {
            throw new IllegalArgumentException("[ERROR] 한나라 장의 출발 지점은 상단부 궁성 내 좌표여야 합니다.");
        }
    }

    private void validateRedGoal(Board board, Position goal) {
        if (!board.isInnerUpperPalace(goal)) {
            throw new IllegalArgumentException("[ERROR] 한나라 장의 목적 지점은 상단부 궁성 내 좌표여야 합니다.");
        }
    }

    @Override
    public List<Position> findPositionsInPath(Position start, Position goal) {
        int columnDifference = Math.abs(start.calculatesColumnDifference(goal));
        int rowDifference = Math.abs(start.calculatesRowDifference(goal));

        if (!isValidDistance(columnDifference, rowDifference)) {
            throw new IllegalArgumentException("[ERROR] 장의 이동 규칙에 어긋나는 움직임입니다.");
        }

        return List.of(goal);
    }

    private boolean isValidDistance(int columnDifference, int rowDifference) {
        int sumMoveDistance = Math.abs(columnDifference) + Math.abs(rowDifference);
        boolean isDiagonal = columnDifference == rowDifference;
        return sumMoveDistance == 1 || (isDiagonal && sumMoveDistance == 2);
    }

    @Override
    protected String getName() {
        return pieceType.getName();
    }

    @Override
    public boolean isSameType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    @Override
    public PieceType getType() {
        return pieceType;
    }
}
