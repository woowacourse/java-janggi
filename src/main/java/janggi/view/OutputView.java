package janggi.view;

import janggi.dto.BoardDto;
import janggi.dto.GameInformationDto;
import janggi.dto.PieceDto;
import janggi.dto.TeamInputDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OutputView {

    private static final int BOARD_WIDTH = 9;
    private static final int BOARD_HEIGHT = 10;
    private static final int NODE_CHARS = 10;
    private static final int SPACER_LINES = 3;
    private static final String EMPTY_NODE = "[　]";

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static final String TEAM_HAN = "HAN";

    private static final List<Integer> DIAGONAL_OFFSETS = List.of(2, 5, 8);

    public void printStartMessage() {
        System.out.println("게임을 시작하겠습니다.");
    }

    public void printMainMenu() {
        System.out.println("[ 메인 메뉴 ]");
        System.out.println("1. 새 게임");
        System.out.println("2. 게임 목록");
        System.out.println("3. 종료");
        System.out.println("> 메뉴 번호를 입력하세요:");
    }

    public void printGameList(List<GameInformationDto> games) {
        System.out.println("[ 게임 목록 ]");

        if (games.isEmpty()) {
            System.out.println("대국 기록이 없습니다.");
            return;
        }

        for (GameInformationDto game : games) {
            System.out.printf("%d번방 %s %s턴\n",
                    game.gameId(),
                    game.status(),
                    game.turn()
            );
        }
        System.out.println();
    }

    public void printNotification() {
        System.out.println("게임을 중단하고 메인 메뉴로 돌아갑니다.");
    }

    public void printReadOnlyModeMessage() {
        System.out.println("종료된 게임입니다.");
    }

    public void printBusinessErrorMessage(String errorMessage) {
        System.out.println("[ERROR] " + errorMessage);
        System.out.println();
    }

    public void printInputErrorMessage(String errorMessage) {
        System.out.println("[WARNING] " + errorMessage);
        System.out.println();
    }

    public void printUndefinedErrorMessage() {
        System.out.println("[FATAL] 예상치 못한 시스템 오류가 발생했습니다.");
        System.out.println();
    }

    public void printBoard(BoardDto boardDto) {
        printXCoordinates();

        for (int row = 0; row < BOARD_HEIGHT; row++) {
            List<String> rowView = generateRowView(row, boardDto.getPieces());
            printNodeRow(rowView, row);

            if (row < BOARD_HEIGHT - 1) {
                printSpacerRows(row);
            }
        }
        System.out.println();
    }

    public void printGameOver(String teamName) {
        System.out.println("게임이 종료되었습니다. " + teamName + "의 승리입니다!");
    }

    public void printScore(int hanScore, int choScore) {
        System.out.println("초나라 현재 점수 : " + hanScore);
        System.out.println("한나라 현재 점수 : " + choScore);
        System.out.println();
    }

    private List<String> generateRowView(int row, List<PieceDto> allPieces) {
        List<String> rowNodes = new ArrayList<>(Collections.nCopies(BOARD_WIDTH, EMPTY_NODE));

        allPieces.stream()
                .filter(piece -> piece.getRow() == row)
                .forEach(piece -> {
                    String colorCode = determineFactionColor(piece);
                    String formattedPiece = String.format("[%s%s%s]", colorCode, piece.getName(), ANSI_RESET);
                    rowNodes.set(piece.getColumn(), formattedPiece);
                });

        return rowNodes;
    }

    private String determineFactionColor(PieceDto piece) {
        if (TEAM_HAN.equals(piece.getTeam())) {
            return ANSI_RED;
        }
        return ANSI_GREEN;
    }

    private void printXCoordinates() {
        StringBuilder xAxis = new StringBuilder("   ");
        for (int col = 0; col < BOARD_WIDTH; col++) {
            char fullWidthNum = (char) ('\uFF10' + col);
            xAxis.append(" ").append(fullWidthNum).append("        ");
        }
        System.out.println(xAxis.toString());
        System.out.println();
    }

    private void printNodeRow(List<String> rowView, int rowIndex) {
        String joinedRow = String.join("-------", rowView);
        System.out.println(rowIndex + "  " + joinedRow);
    }

    private void printSpacerRows(int row) {
        for (int spacerIndex = 0; spacerIndex < SPACER_LINES; spacerIndex++) {
            List<String> spacerRow = new ArrayList<>(Collections.nCopies(BOARD_WIDTH * NODE_CHARS, " "));

            for (int col = 0; col < BOARD_WIDTH; col++) {
                spacerRow.set(col * NODE_CHARS + 1, "ㅣ");
            }

            int offset = DIAGONAL_OFFSETS.get(spacerIndex);
            addPalaceDiagonals(spacerRow, row, offset);

            System.out.print("   ");
            System.out.println(String.join("", spacerRow));
        }
    }

    private void addPalaceDiagonals(List<String> spacerRow, int row, int offset) {
        int x3Center = 3 * NODE_CHARS + 1;
        int x4Center = 4 * NODE_CHARS + 1;
        int x5Center = 5 * NODE_CHARS + 1;

        if (row == 0 || row == 7) {
            spacerRow.set(x3Center + offset, "\\");
            spacerRow.set(x5Center - offset, "/");
        } else if (row == 1 || row == 8) {
            spacerRow.set(x4Center - offset, "/");
            spacerRow.set(x4Center + offset, "\\");
        }
    }
}
