package view;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.Country;

public class OutputView {

    public void printJanggiBoard(JanggiBoard board) {
        StringBuilder builder = new StringBuilder();

        for (int row = JanggiBoard.BOARD_MIN_SIZE; row <= JanggiBoard.ROW_SIZE; row++) {
            for (int col = JanggiBoard.BOARD_MIN_SIZE; col <= JanggiBoard.COL_SIZE; col++) {
                JanggiCoordinate coordinate = new JanggiCoordinate(row, col);
                if (board.isBlankCoordinate(coordinate)) {
                    builder.append("＿");
                    continue;
                }
                Country country = board.findCountryByCoordinate(coordinate);
                if (country == Country.CHO) {
                    builder.append("\u001B[32m").append(board.getPieceType(coordinate)).append("\u001B[0m");
                }
                if (country == Country.HAN) {
                    builder.append("\u001B[31m").append(board.getPieceType(coordinate)).append("\u001B[0m");
                }
            }
            builder.append('\n');
        }
        System.out.println(builder);
    }
}
