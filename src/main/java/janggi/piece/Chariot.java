package janggi.piece;

import janggi.board.Position;

import java.util.Map;

public class Chariot extends Piece {
    private static final PieceType TYPE = PieceType.CHARIOT;

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(Map<Position, Piece> board, Position start, Position goal) {
        validateStraightMove(start, goal);
        Piece attacker = board.get(start);
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
        return TYPE.getName();
    }

    @Override
    public boolean isSameType(PieceType pieceType) {
        return pieceType == TYPE;
    }
}
