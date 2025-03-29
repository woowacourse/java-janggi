package janggi.piece;

import janggi.board.Board;
import janggi.board.Position;

public class Chariot extends Piece {
    private final PieceType pieceType;

    public Chariot(Team team) {
        super(team);
        this.pieceType = PieceType.CHARIOT;
    }

    @Override
    public void validateMovable(Board board, Position start, Position goal) {
        if (isInsidePalace(board, start)) {
            validatePalaceMove(board, start, goal);
        } else {
            validateStraightMove(start, goal);
        }
        validatePath(board, start, goal);
        validateNonOurArmyAtGoal(board, goal, board.getPiece(start).getTeam());
    }

    private void validatePalaceMove(Board board, Position start, Position goal) {
        if (!isInsidePalace(board, goal)) {
            // 궁성을 벗어나면 직선 이동만 허용
            validateStraightMove(start, goal);
            return;
        }

        if (isDiagonalMove(start, goal) && !passThroughPalaceCenter(board, start, goal)) {
            throw new IllegalArgumentException("[ERROR] 궁성 내부에서는 중앙을 거쳐야만 대각선 이동이 가능합니다.");
        }
    }

    private boolean isInsidePalace(Board board, Position position) {
        return board.isInnerUpperPalace(position) || board.isInnerBottomPalace(position);
    }

    private boolean isDiagonalMove(Position start, Position goal) {
        int columnDifference = Math.abs(start.calculatesColumnDifference(goal));
        int rowDifference = Math.abs(start.calculatesRowDifference(goal));
        return columnDifference == rowDifference;
    }

    private boolean passThroughPalaceCenter(Board board, Position start, Position goal) {
        Position center = findPalaceCenter(board, start);
        return start.isConnectedTo(center) && center.isConnectedTo(goal);
    }

    private Position findPalaceCenter(Board board, Position position) {
        return board.isInnerBottomPalace(position) ? board.getBottomPalaceCenter() : board.getUpperPalaceCenter();
    }

    private void validateStraightMove(Position start, Position goal) {
        if (!start.equalColumn(goal) && !start.equalRow(goal)) {
            throw new IllegalArgumentException("[ERROR] 차는 상하좌우 일직선으로만 이동 가능합니다.");
        }
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
