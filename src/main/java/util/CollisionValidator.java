package util;

import domain.Position;
import domain.Team;
import domain.piece.Piece;
import domain.piece.PieceProvider;

public class CollisionValidator {

    private static final int ROW_SIZE = 10;
    private static final int COLUMN_SIZE = 9;

    public static boolean canMoveToTarget(Position targetPosition, PieceProvider board, Team team) {
        if (!isWithinBoardPosition(targetPosition)) {
            return false;
        }
        if (board.isBlank(targetPosition)) {
            return true;
        }
        Piece targetPiece = board.getPiece(targetPosition);
        return targetPiece.getTeam() != team;
    }

    public static boolean isWithinBoard(int row, int column) {
        return row >= 0 && row < ROW_SIZE && column >= 0 && column < COLUMN_SIZE;
    }

    public static boolean isWithinBoardPosition(Position position) {
        return isWithinBoard(position.getRows(), position.getColumns());
    }
}
