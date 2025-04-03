package janggi.view;

import janggi.piece.Piece;
import janggi.position.Position;
import janggi.view.format.PieceFormat;
import java.util.Arrays;
import java.util.Map;

public class OutputView {
    private static final int BOARD_LOWER_BOUND_X = 1;
    private static final int BOARD_UPPER_BOUND_X = 9;
    private static final int BOARD_LOWER_BOUND_Y = 1;
    private static final int BOARD_UPPER_BOUND_Y = 10;

    private final PieceFormat pieceFormat;

    public OutputView(final PieceFormat pieceFormat) {
        this.pieceFormat = pieceFormat;
    }

    public void printBoard(final Map<Position, Piece> board) {
        final String[][] boardData = createEmptyBoard();
        for (var entry : board.entrySet()) {
            final String message = pieceFormat.getMessage(entry.getValue());
            boardData[entry.getKey().x()][entry.getKey().y()] = message;
        }
        printBoard(boardData);
    }

    private String[][] createEmptyBoard() {
        final String[][] boardData = new String[BOARD_UPPER_BOUND_X + 1][BOARD_UPPER_BOUND_Y + 1];
        for (int x = BOARD_LOWER_BOUND_X; x <= BOARD_UPPER_BOUND_X; ++x) {
            Arrays.fill(boardData[x], ".");
        }
        return boardData;
    }

    private void printBoard(final String[][] boardData) {
        for (int y = BOARD_LOWER_BOUND_Y; y <= BOARD_UPPER_BOUND_Y; ++y) {
            for (int x = BOARD_LOWER_BOUND_X; x <= BOARD_UPPER_BOUND_X; ++x) {
                System.out.printf("%s\t", boardData[x][y]);
            }
            System.out.printf("\t%d%n", y % BOARD_UPPER_BOUND_Y);
        }
        System.out.println();
        System.out.println("1\t2\t3\t4\t5\t6\t7\t8\t9");
    }

    public void printScore(final double redScore, final double blueScore) {
        System.out.printf("%nScore: %.1f - %.1f%n", redScore, blueScore);
    }

    public void printNewGame() {
        System.out.println("새로운 게임을 시작합니다");
    }

    public void printContinueGame() {
        System.out.println("이전 게임을 이어서 진행합니다");
    }

    public void printLoadJanngiIllegalInput() {
        System.out.println("잘못된 입력입니다. 이전 게임을 이어서 진행합니다. 새로운 게임을 시작하려면 프로그램을 재시작 후 'new' 입력하세요.");
    }

    public void printMoveResult(final boolean isMoveSuccess) {
        final String message = formatMoveResult(isMoveSuccess);
        System.out.println(message);
    }

    private String formatMoveResult(final boolean isMoveSuccess) {
        if (isMoveSuccess) {
            return "이동 성공";
        }
        return "이동 실패, 입력한 좌표를 확인해 주세요.";
    }

    public void printDatabaseOffline() {
        System.out.println("데이터베이스가 오프라인입니다. 게임이 저장되지 않습니다.");
    }

    public void printDatabaseLoadError() {
        System.out.println("데이터베이스로부터 이전 게임을 불러오는데 실패했습니다. 새 게임을 시작합니다.");
    }

    public void printSaveResult(final boolean isSaveSuccess) {
        final String message = formatSaveResult(isSaveSuccess);
        System.out.println(message);
    }

    private String formatSaveResult(final boolean isSaveSuccess) {
        if (isSaveSuccess) {
            return "저장 성공";
        }
        return "게임 저장에 실패했습니다. 오프라인 모드로 진행되어 게임이 저장되지 않습니다.";
    }
}
