package janggi.piece;

import janggi.board.Board;
import janggi.board.Position;

public class Elephant extends Piece {
    private static final int ELEPHANT_MOVE_LONG = 3;
    private static final int ELEPHANT_MOVE_SHORT = 2;

    private final PieceType pieceType;

    public Elephant(Team team) {
        super(team);
        this.pieceType = PieceType.ELEPHANT;
    }

    @Override
    public void validateMovable(Board board, Position start, Position goal) {
        validateElephantMove(start, goal);
        Piece attacker = board.getPiece(start);
        validatePath(board, start, goal);
        validateNonOurArmyAtGoal(board, goal, attacker.getTeam());
    }

    private void validateElephantMove(Position start, Position goal) {
        int dColumn = Math.abs(start.calculatesColumnDifference(goal));
        int dRow = Math.abs(start.calculatesRowDifference(goal));
        if ((dColumn != ELEPHANT_MOVE_SHORT || dRow != ELEPHANT_MOVE_LONG) && (dColumn != ELEPHANT_MOVE_LONG || dRow != ELEPHANT_MOVE_SHORT)) {
            throw new IllegalArgumentException("[ERROR] 상의 이동 규칙에 어긋나는 움직임입니다.");
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
