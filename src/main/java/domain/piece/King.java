package domain.piece;

import domain.board.Board;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Side side) {
        super(side);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position start) {
        List<Position> possiblePositions = new ArrayList<>();
        int[] dx = {0, -1, 1, 0};
        int[] dy = {-1, 0, 0, 1};

        for (int i = 0; i < 4; i++) {
            int nRow = start.row() + dx[i];
            int nCol = start.col() + dy[i];
            Position destination = new Position(nCol, nRow);

            if (board.isAvailableDestination(destination)) {
                possiblePositions.add(destination);
            }
        }

        return possiblePositions;
    }
}
