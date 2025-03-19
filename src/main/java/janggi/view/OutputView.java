package janggi.view;

import janggi.domain.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;

import java.util.Map;

public class OutputView {

    public void printBoard(Board board) {
        Map<Position, Piece> pieces = board.getPieces();
        for(int i = 1; i <= 10; i ++) {
            for(int j = 1; j <= 9; j ++) {
                System.out.printf("%2s ", pieces.get(new Position(i, j)).getName());
            }
            System.out.println();
        }
    }
}
