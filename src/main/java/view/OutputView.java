package view;

import domain.Piece;
import domain.vo.Position;

import java.util.Map;

public class OutputView {

    public void printBoard(Map<Position, Piece> board) {
        for (int row = 9; row >= 0; row--) {
            for (int col = 0; col <= 8; col++) {
                Position position = Position.of(row, col);
                if (board.containsKey(position)) {
                    Piece piece = board.get(position);
                    System.out.print(piece.getTypeName() + "/" + piece.getTeamName() + " ");
                } else {
                    System.out.print("x ");
                }
            }
            System.out.println();
        }
    }
}
