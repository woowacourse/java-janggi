package domain.piece;

import domain.board.Board;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {

    public Elephant(Side side) {
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
            if (!board.isEmpty(firstMovePosition)) {
                continue;
            }

            for (int secondMoveDirection : secondMoveDirections) {
                int nRow2 = nRow;
                int nCol2 = nCol;
                Position destination = new Position(nCol2, nRow2);

                for (int j = 0; j < 2; j++) {
                    if (dx[i] == 0) {
                        nRow2 += secondMoveDirection;
                        nCol2 += dy[i];

                        Position secondMovePosition = new Position(nCol2, nRow2);

                        if (j == 0) {
                            if (!board.isAvailableDestination(secondMovePosition)) {
                                break;
                            }
                            if (!board.isEmpty(secondMovePosition)) {
                                break;
                            }
                        }

                        destination = new Position(nCol2, nRow2);
                    }

                    if (dy[i] == 0) {
                        nRow2 += dx[i];
                        nCol2 += secondMoveDirection;

                        Position secondMovePosition = new Position(nCol2, nRow2);

                        if (j == 0) {
                            if (!board.isAvailableDestination(secondMovePosition)) {
                                break;
                            }
                            if (!board.isEmpty(secondMovePosition)) {
                                break;
                            }
                        }

                        destination = new Position(nCol2, nRow2);
                    }

                    if (j == 1 && board.isAvailableDestination(destination)) {
                        possiblePositions.add(destination);
                    }
                }
            }
        }

        return possiblePositions;
    }
}
