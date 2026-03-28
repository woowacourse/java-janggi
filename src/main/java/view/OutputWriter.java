package view;

import domain.board.dto.JanggiBoardView;
import domain.piece.Team;
import domain.point.Point;

import java.util.Map;

public class OutputWriter {

    private static final int MAX_ROW = 10;
    private static final int MAX_FILE = 9;
    private static final String ROW_NUMBER_FORMAT = "%d   ";
    private static final String FILE_NUMBER_FORMAT = "   %d  ";
    private static final String BOARD_HEADER_PADDING = "    ";
    private static final String PIECE_SEPARATOR = " ";

    public void printJanggiBoard(JanggiBoardView boardView) {
        printFileNumber();
        printJanggiBoard(boardView.boardViews());
    }

    public void printWinner(Team team) {
        System.out.println(team + "팀의 승리입니다!");
    }

    private void printJanggiBoard(Map<Point, PieceView> boardView) {
        for (int y = 0; y < MAX_ROW; y++) {
            System.out.printf(ROW_NUMBER_FORMAT, y);
            for (int x = 0; x < MAX_FILE; x++) {
                PieceView view = boardView.getOrDefault(new Point(y, x), PieceView.NONE);
                System.out.print(view.getViewMessage() + PIECE_SEPARATOR);
            }
            System.out.println();
        }
    }

    private void printFileNumber() {
        System.out.print(BOARD_HEADER_PADDING);
        for (int x = 0; x < MAX_FILE; x++) {
            System.out.printf(FILE_NUMBER_FORMAT, x);
        }
        System.out.println();
    }

}
