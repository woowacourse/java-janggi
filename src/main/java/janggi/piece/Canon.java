package janggi.piece;

import janggi.board.Board;
import janggi.board.Position;

import java.util.List;

public class Canon extends Piece {
    protected static final PieceType TYPE = PieceType.CANNON;

    public Canon(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(Board board, Position start, Position goal) {
        validateStraightMove(start, goal);
        validatePath(board, start, goal);
        validatePieceOnGoal(board, goal);
    }

    protected void validatePath(Board board, Position start, Position goal) {
        List<Position> positionsInPath = findPositionsInPath(start, goal);
        int pieceCount = 0;
        for (Position position : positionsInPath) {
            boolean existsPiece = board.existPiece(position);
            if (!existsPiece) {
                continue;
            }
            Piece piece = board.getPiece(position);
            if (piece.isSameType(PieceType.CANNON)) {
                throw new IllegalArgumentException("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
            }
            pieceCount++;
        }
        if (pieceCount != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 다른 기물 1개를 넘어가야 합니다.");
        }
    }

    private void validateStraightMove(Position start, Position goal) {
        if (!start.equalColumn(goal) && !start.equalRow(goal)) {
            throw new IllegalArgumentException("[ERROR] 포의 이동 규칙에 어긋나는 움직임입니다.");
        }
    }

    protected void validatePieceOnGoal(Board board, Position goal) {
        Piece other = board.getPiece(goal);
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
