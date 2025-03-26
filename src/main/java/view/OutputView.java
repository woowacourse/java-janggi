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
            if (row == 1) {
                for (int col = 1; col <= MAX_COL; col++) {
                    builder.append(" ").append(col);
                }
                builder.append('\n');
            }
            for (int col = 1; col <= MAX_COL; col++) {
                if (col == 1) {
                    builder.append(row);
                }
                Coordinate coordinate = new Coordinate(row, col);
                if (board.isBlankCoordinate(coordinate)) {
                    builder.append("＿");
                    continue;
                }
                Country country = board.findCountryByCoordinate(coordinate);
                if (country == Country.CHO) {
                    builder.append("\u001B[32m").append(board.findPieceTypeByCoordinate(coordinate).getDescription())
                            .append("\u001B[0m");
                }
                if (country == Country.HAN) {
                    builder.append("\u001B[31m").append(board.findPieceTypeByCoordinate(coordinate).getDescription())
                            .append("\u001B[0m");
                }
            }
            builder.append('\n');
        }
        System.out.println(builder);
    }

    public void printEndGame(boolean isChoGungDead, boolean isHanGungDead) {
        if (isChoGungDead) {
            System.out.println("한나라가 승리하였습니다.");
        }
        if (isHanGungDead) {
            System.out.println("조나라가 승리하였습니다.");
        }
    }
}
