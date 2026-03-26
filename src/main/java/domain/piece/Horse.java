package domain.piece;

import domain.board.Board;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {

    public Horse(Side side) {
        super(side);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position start) {
        List<Position> possiblePositions = new ArrayList<>();
        int[] dx = {0, -1, 1, 0};
        int[] dy = {-1, 0, 0, 1};

        int[] secondMoveDirections = {-1, 1};

        for (int i = 0; i < 4; i++) {
            int nRow = start.row() + dx[i];
            int nCol = start.col() + dy[i];
            Position firstMovePosition = new Position(nCol, nRow);
            if (!board.isAvailableDestination(firstMovePosition)) {
                continue;
            }
            if (!board.isEmpty(firstMovePosition)) { // 2차
                continue;
            }
            for (int secondMoveDirection : secondMoveDirections) {
                if (dx[i] == 0) {
                    int nRow2 = nRow + secondMoveDirection;
                    int nCol2 = nCol + dy[i];
                    Position destination = new Position(nCol2, nRow2);
                    if (board.isAvailableDestination(destination)) {
                        possiblePositions.add(destination);
                    }
                }
                if (dy[i] == 0) {
                    int nRow2 = nRow + dx[i];
                    int nCol2 = nCol + secondMoveDirection;
                    Position destination = new Position(nCol2, nRow2);
                    if (board.isAvailableDestination(destination)) {
                        possiblePositions.add(destination);
                    }
                }
            }
        }

        return possiblePositions;
    }
}
