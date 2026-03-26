package domain.piece;

import domain.board.Board;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(Side side) {
        super(side);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position start) {
        List<Position> possiblePositions = new ArrayList<>();
        int[] dx = {0, -1, 1, 0};
        int[] dy = {-1, 0, 0, 1};

        for (int i = 0; i < 4; i++) {
            int nRow = start.row();
            int nCol = start.col();

            while (true) {
                nRow += dx[i];
                nCol += dy[i];
                Position destination = new Position(nCol, nRow);

                if (!board.isAvailableDestination(destination)) {
                    break;
                }

                possiblePositions.add(destination);

                if (board.isOpponentPiece(destination)) {
                    break;
                }
            }
        }

        return possiblePositions;
    }
}
