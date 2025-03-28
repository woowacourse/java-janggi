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
        validateStraightMove(start, goal);
        Piece attacker = board.getPiece(start);
        validatePath(board, start, goal);
        validateNonOurArmyAtGoal(board, goal, attacker.getTeam());
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
}
