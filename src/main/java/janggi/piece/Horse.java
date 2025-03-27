package janggi.piece;

import janggi.board.Board;
import janggi.board.Position;

public class Horse extends Piece {
    private static final PieceType TYPE = PieceType.HORSE;
    private static final int HORSE_MOVE_LONG = 2;
    private static final int HORSE_MOVE_SHORT = 1;

    public Horse(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(Board board, Position start, Position goal) {
        validateHorseMove(start, goal);
        Piece attacker = board.getPiece(start);
        validatePath(board, start, goal);
        validateNonOurArmyAtGoal(board, goal, attacker.getTeam());
    }

    private void validateHorseMove(Position start, Position goal) {
        int dColumn = Math.abs(start.calculatesColumnDifference(goal));
        int dRow = Math.abs(start.calculatesRowDifference(goal));
        if ((dColumn != HORSE_MOVE_SHORT || dRow != HORSE_MOVE_LONG) && (dColumn != HORSE_MOVE_LONG || dRow != HORSE_MOVE_SHORT)) {
            throw new IllegalArgumentException("[ERROR] 마의 이동 규칙에 어긋나는 움직임입니다.");
        }
    }

    @Override
    protected String getName() {
        return TYPE.getName();
    }

    @Override
    public boolean isSameType(PieceType pieceType) {
        return pieceType == TYPE;
    }
}
