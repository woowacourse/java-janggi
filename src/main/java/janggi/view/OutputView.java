package janggi.view;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import java.util.Map;

public class OutputView {

    public void printBoard(final Map<Position, Piece> board) {
        for (int row = 1; row <= 10; row++) {
            System.out.printf("%-2d", row);
            for (int column = 1; column <= 9; column++) {
                printPieceInBoard(board, row, column);
            }
            System.out.println();
        }
        System.out.println("  １２３４５６７８９");
    }

    private void printPieceInBoard(final Map<Position, Piece> board, final int row, final int column) {
        Position position = Position.of(row, column);
        if (board.containsKey(position)) {
            Piece piece = board.get(position);
            System.out.print(piece.getName());
            return;
        }
        System.out.print("＿");
    }

    public void printEndMessage() {
        System.out.println("게임을 종료합니다.");
    }
}
