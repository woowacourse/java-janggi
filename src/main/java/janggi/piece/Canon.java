package janggi.piece;

import janggi.board.Position;

import java.util.List;
import java.util.Map;

public class Canon extends Piece {
    protected static final PieceType TYPE = PieceType.CANNON;

    public Canon(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(Map<Position, Piece> board, Position start, Position goal) {
        validatePath(board, start, goal);
        validatePieceOnGoal(board, goal);
    }

    protected void validatePath(Map<Position, Piece> board, Position start, Position goal) {
        List<Position> positionsInPath = findPositionsInPath(start, goal);
        int pieceCount = 0;
        for (Position position : positionsInPath) {
            boolean existsPiece = board.containsKey(position);
            if (!existsPiece) {
                continue;
            }
            Piece piece = board.get(position);
            if (piece.isSameType(PieceType.CANNON)) {
                throw new IllegalArgumentException("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
            }
            pieceCount++;
        }
        if (pieceCount != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 다른 기물 1개를 넘어가야 합니다.");
        }
    }

    protected void validatePieceOnGoal(Map<Position, Piece> board, Position goal) {
        Piece other = board.get(goal);
        if (other != null && other.isSameType(PieceType.CANNON)) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 잡을 수 없습니다.");
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
