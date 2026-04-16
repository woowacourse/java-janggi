package janggi.view;

import janggi.domain.Team;
import janggi.dto.BoardDto;
import janggi.dto.BoardDto.CoordinateDto;
import janggi.dto.GameRoomDto;
import janggi.dto.PieceDto;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final int BOARD_WIDTH = 9;
    private static final int BOARD_HEIGHT = 10;
    private static final String EMPTY_CHAR = "　";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    public void printStartMessage() {
        System.out.println("게임을 시작하겠습니다.\n");
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void printBoard(BoardDto boardDto) {
        String[][] boardView = generateBoardView(boardDto);

        System.out.println();
        printXAxis();
        printRows(boardView);
        System.out.println();
    }

    private void printRows(String[][] boardView) {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            printRow(y, boardView[y]);
        }
    }

    private void printRow(int y, String[] row) {
        System.out.printf("%d ", y);
        for (String cell : row) {
            System.out.print(cell);
        }
        System.out.println();
    }

    private String[][] generateBoardView(BoardDto boardDto) {
        String[][] boardView = createEmptyBoard();
        fillPieces(boardView, boardDto.getPieces());
        return boardView;
    }

    private String[][] createEmptyBoard() {
        String[][] boardView = new String[BOARD_HEIGHT][BOARD_WIDTH];
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            fillEmptyRow(boardView[y]);
        }
        return boardView;
    }

    private void fillEmptyRow(String[] row) {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            row[x] = String.format("[%s]", EMPTY_CHAR);
        }
    }

    private void fillPieces(String[][] boardView, Map<CoordinateDto, PieceDto> pieces) {
        for (Map.Entry<CoordinateDto, PieceDto> entry : pieces.entrySet()) {
            placePiece(boardView, entry.getKey(), entry.getValue());
        }
    }

    private void placePiece(String[][] boardView, CoordinateDto coord, PieceDto piece) {
        String color = decideColor(piece.team());
        boardView[coord.column()][coord.row()] = String.format("[%s%s%s]", color, piece.name(), ANSI_RESET);
    }

    private String decideColor(Team team) {
        if (team == Team.HAN) {
            return ANSI_RED;
        }
        return ANSI_GREEN;
    }

    private void printXAxis() {
        System.out.print("  ");
        for (int x = 0; x < BOARD_WIDTH; x++) {
            printFullWidthDigit(x);
        }
        System.out.println();
    }

    private void printFullWidthDigit(int digit) {
        char fullWidthDigit = (char) ('\uFF10' + digit);
        System.out.print(" " + fullWidthDigit + " ");
    }

    public void printMenu() {
        System.out.println("[메뉴]");
        System.out.println("━━━━━━━━━━━━━━━━━━");
        System.out.println("1. 새 게임 시작");
        System.out.println("2. 게임 목록 보기");
        System.out.println("3. 게임 종료");
        System.out.println("━━━━━━━━━━━━━━━━━━");
        System.out.print("메뉴를 선택하세요: ");
    }

    public void printScore(double choscore, double hanscore) {
        System.out.println("[기물 점수]");
        System.out.printf("초나라: %.1f점%n", choscore);
        System.out.printf("한나라: %.1f점%n", hanscore);
    }

    public void printFinalResult(Team winner, double choScore, double hanScore) {
        System.out.println();
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf(" 왕(궁)이 잡혔습니다! %s나라의 승리입니다!%n", winner.getName());
        System.out.println();
        System.out.println(" [최종 점수]");
        System.out.printf(" 초나라 : %.1f점%n", choScore);
        System.out.printf(" 한나라 : %.1f점%n", hanScore);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println();
    }

    public void printCheckMessage(Team team) {
        System.out.println();
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.printf("  장군입니다! %s나라 왕이 위험합니다! 기물을 움직여 궁을 살리세요.%n", team.getName());
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }

    public void printGameList(List<GameRoomDto> games) {
        System.out.println("\n[저장된 게임 목록]");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (GameRoomDto game : games) {
            System.out.printf("ID: %d | 생성일: %s | 상태: %s | 순서: %s | 초: %.1f 한: %.1f%n",
                    game.getId(),
                    game.getCreatedAt(),
                    game.getStatus(),
                    game.getCurrentTurn(),
                    game.getChoScore(),
                    game.getHanScore());
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println();
    }
}
