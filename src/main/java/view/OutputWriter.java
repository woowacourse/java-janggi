package view;

import database.dto.BoardSummaryDto;
import view.dto.JanggiBoardDto;
import view.dto.PieceViewDto;
import domain.piece.Team;
import domain.point.Point;

import java.util.List;
import java.util.Map;

import static common.constant.JanggiConstant.*;

public class OutputWriter {

    private static final String ROW_NUMBER_FORMAT = "%d　 ";      // 전각 공백
    private static final String FILE_NUMBER_FORMAT = "　 %d 　";    // 전각 공백
    private static final String BOARD_HEADER_PADDING = "　　 ";
    private static final String PIECE_SEPARATOR = "　";           // 전각 공백
    private static final String WINNER_MESSAGE = "%s팀의 승리입니다!";
    private static final String ERROR_MESSAGE_PREFIX = "［ERROR］ ";
    private static final String BOARD_SUMMARY_FORMAT = "%d번방: 현재 %s턴 \n";

    public void printExistingPlayingBoard(List<BoardSummaryDto> boardSummaries) {
        System.out.println("현재 종료되지 않은 장기판 목록입니다");
        for (BoardSummaryDto summary : boardSummaries) {
            System.out.printf(BOARD_SUMMARY_FORMAT, summary.boardId(), summary.currentTurn());
        }
    }

    public void printJanggiBoard(JanggiBoardDto boardView) {
        printFileNumber();
        printBoard(boardView.boardViews());
    }

    public void printWinner(Team team) {
        System.out.printf(WINNER_MESSAGE, team);
    }

    private void printBoard(Map<Point, PieceViewDto> boardView) {
        for (int y = BASE_POINT; y < MAX_ROW; y++) {
            System.out.printf(ROW_NUMBER_FORMAT, y);
            for (int x = BASE_POINT; x < MAX_FILE; x++) {
                PieceViewDto view = boardView.get(new Point(y, x));
                System.out.print(view.getColoredMessage() + PIECE_SEPARATOR);
            }
            System.out.println();
        }
    }

    private void printFileNumber() {
        System.out.print(BOARD_HEADER_PADDING);
        for (int x = BASE_POINT; x < MAX_FILE; x++) {
            System.out.printf(FILE_NUMBER_FORMAT, x);
        }
        System.out.println();
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(ERROR_MESSAGE_PREFIX + errorMessage);
    }
}
