package view;

import static domain.Coordinate.MAX_COL;
import static domain.Coordinate.MAX_ROW;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.Country;

public class OutputView {

    public void printJanggiBoard(Board board) {
        StringBuilder builder = new StringBuilder();

        for (int row = 1; row <= MAX_ROW; row++) {
            for (int col = 1; col <= MAX_COL; col++) {
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
