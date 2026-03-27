package domain.piece;

import domain.board.Board;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;


public class Pawn extends Piece {

    public Pawn(Side side) {
        super(side);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position start) {
        List<Position> possiblePositions = new ArrayList<>();
        int[][] moves = {
                {this.forward(), 0},
                {0, -1},
                {0, 1}
        };

        for (int[] move : moves) {
            int nRow = start.row() + move[1];
            int nCol = start.col() + move[0];

            Position destination = new Position(nCol, nRow);

            if (board.isAvailableDestination(destination)) {
                possiblePositions.add(destination);
            }
        }

        return possiblePositions;
    }
}
