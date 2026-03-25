package janggi.domain.mouveRule;

import janggi.domain.Board;
import janggi.domain.vo.Position;
import java.util.ArrayList;
import java.util.List;

public class TankMoveRule implements MoveRule {
    @Override
    public void move(Position from, Position to, Board board) {
        int fromRow = from.getRow();
        int fromCol = from.getCol();

        int toRow = to.getRow();
        int toCol = to.getCol();

        if ((fromRow != toRow) && (fromCol != toCol)) {
            throw new IllegalArgumentException("직선으로만 이동할 수 있습니다.");
        }
        validatePath(board, fromRow, toRow, fromCol, toCol);

    }

    private static void validatePath(Board board, int fromRow, int toRow, int fromCol, int toCol) {
        List<Position> pathPositions = new ArrayList<>();
        int startIndex = -1;
        int endIndex = -1;

        if (fromRow == toRow) { // 행이동
            startIndex = Math.min(fromCol, toCol);
            endIndex = Math.max(fromCol, toCol);

            for (int index = startIndex + 1; index < endIndex; index++) {
                pathPositions.add(new Position(toRow, index));
            }
        }

        if (fromCol == toCol) { // 열이동
            startIndex = Math.min(fromRow, toRow);
            endIndex = Math.max(fromRow, toRow);
            for (int index = startIndex + 1; index < endIndex; index++) {
                pathPositions.add(new Position(index, toCol));
            }
        }

        for (Position position : pathPositions) {
            if (!board.isEmptyPosition(position)) {
                throw new IllegalArgumentException("중간 경로에 기물이 있습니다.");
            }
        }
    }
}
