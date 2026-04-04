package domain.strategy;

import domain.Board;
import domain.Piece;
import domain.Team;
import domain.vo.Position;

public class ChariotMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isNotStraightPath(from, to) &&
                (isOutsideOwnPalace(from, to, board) || isNotDiagonalPath(from, to)))
            return false;

        int dx = Integer.compare(to.getRow(), from.getRow());
        int dy = Integer.compare(to.getCol(), from.getCol());

        int row = from.getRow();
        int col = from.getCol();

        while (row != to.getRow() || col != to.getCol()) {
            row += dx;
            col += dy;

            if (row == to.getRow() && col == to.getCol()) {
                break;
            }
            if (board.isExistPosition(Position.of(row, col))) {
                return false;
            }
        }

        return board.canOccupy(from, to);
    }

    private boolean isOutsideOwnPalace(Position from, Position to, Board board) {
        Piece piece = board.findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 이동할 기물이 없습니다."));
        Team team = piece.getTeam();

        if (!board.isInOwnPalace(from, team)) {
            return true;
        }

        if (!board.isInOwnPalace(to, team)) {
            return true;
        }
        return false;
    }

    private boolean isNotDiagonalPath(Position from, Position to) {
        if (Math.abs(from.getRow() - to.getRow()) == Math.abs(from.getCol() - to.getCol())) {
            return false;
        }
        return true;
    }

    private boolean isNotStraightPath(final Position from, final Position to) {
        return from.getCol() != to.getCol() && from.getRow() != to.getRow();
    }
}
