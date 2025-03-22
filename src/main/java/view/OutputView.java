package view;

import static domain.Coordinate.BOARD_MIN_SIZE;
import static domain.Coordinate.COL_SIZE;
import static domain.Coordinate.ROW_SIZE;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.Country;

public class OutputView {

    public void printJanggiBoard(Board board) {
        StringBuilder builder = new StringBuilder();

        for (int row = BOARD_MIN_SIZE; row <= ROW_SIZE; row++) {
            for (int col = BOARD_MIN_SIZE; col <= COL_SIZE; col++) {
                Coordinate coordinate = new Coordinate(row, col);
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
