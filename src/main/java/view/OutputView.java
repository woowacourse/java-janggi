package view;

import model.board.Board;
import model.coordinate.Position;
import model.piece.Piece;

import java.util.Map;

import static view.formater.BoardFormatter.COL_NUM;
import static view.formater.BoardFormatter.RED;
import static view.formater.BoardFormatter.RESET;
import static view.formater.BoardFormatter.ROW_NUM;
import static view.formater.BoardFormatter.SPACE;
import static view.formater.BoardFormatter.VERTICAL_LINE;
import static view.formater.BoardFormatter.formatHorizon;
import static view.formater.BoardFormatter.formatSymbol;

public class OutputView {

    public void displayBoard(Map<Position, Piece> board) {
        StringBuilder outputBuilder = new StringBuilder();
        String border = formatHorizon(Board.BOARD_COL);
        appendColIndex(outputBuilder);
        outputBuilder.append(border).append(System.lineSeparator());
        appendRows(outputBuilder, board);
        outputBuilder.append(border).append(System.lineSeparator());
        System.out.print(outputBuilder);
    }

    private static void appendColIndex(StringBuilder outputBuilder) {
        outputBuilder.append(System.lineSeparator())
                .append(SPACE).append(SPACE).append(SPACE);
        for (String column : COL_NUM) {
            outputBuilder.append(SPACE).append(column);
        }
        outputBuilder.append(System.lineSeparator());
    }

    private static void appendRows(StringBuilder outputBuilder, Map<Position, Piece> board) {
        for (int row = 0; row < Board.BOARD_ROW; row++) {
            appendRow(outputBuilder, board, row);
        }
    }

    private static void appendRow(StringBuilder outputBuilder, Map<Position, Piece> board, int row) {
        outputBuilder.append(ROW_NUM[row]).append(" ").append(VERTICAL_LINE);
        for (int col = 0; col < Board.BOARD_COL; col++) {
            Piece piece = board.get(new Position(row, col));
            outputBuilder.append(SPACE).append(formatSymbol(piece));
        }
        outputBuilder.append(SPACE).append(VERTICAL_LINE).append(System.lineSeparator());
    }

    public void displayError(String message) {
        System.out.println(RED + "[ERROR] " + message + RESET);
    }

    public void displayWinner(String winnerName) {
        System.out.printf("%n%s가 승리했습니다!%n", winnerName);
    }

    public void displayScores(double choScore, double hanScore) {
        System.out.printf("%n초나라 점수: %.1f%n", choScore);
        System.out.printf("한나라 점수: %.1f%n", hanScore);
    }

    public void displaySaved() {
        System.out.println("\n게임이 저장되었습니다.");
    }

    public void displayResume() {
        System.out.println("[이전에 진행하던 게임을 불러왔습니다.]");
        System.out.println();
    }
}