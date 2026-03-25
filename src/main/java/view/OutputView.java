package view;

import java.util.List;

public class OutputView {
    private static final int WIDTH = 9;
    private static final int HEIGHT = 10;

    public void printBoard(List<List<String>> board) {
        validateBoard(board);
        System.out.println();
        printColumnHeader();
        for (int y = 1; y <= 9; y++) {
            printPieceRow(y, board.get(y));
            printVerticalRow();
        }
        printPieceRow(0, board.getFirst());
        System.out.println();
    }

    public void printChoNameInput() {
        System.out.println("선공 닉네임을 입력하세요.");
    }

    public void printHanNameInput() {
        System.out.println("후공 이름을 입력하세요.");
    }

    public void printChoPositionInput() {
        System.out.println("선공 배치 선택 (1 - 상마상마, 2 - 마상마상, 3 - 마상상마, 4 - 상마마상)");
    }

    public void printHanPositionInput() {
        System.out.println("후공 배치 선택 (1 - 상마상마, 2 - 마상마상, 3 - 마상상마, 4 - 상마마상)");
    }

    public void printPieceSelectInput() {
        System.out.println("기물 선택");
    }

    public void printPieceMovePositionInput() {
        System.out.println("이동 위치");
    }


    private void printColumnHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("       ");
        for (int x = 1; x <= WIDTH; x++) {
            sb.append(x);
            if (x != WIDTH) {
                sb.append(" ---- ");
            }
        }
        System.out.println(sb);
        System.out.println();
    }

    private void printPieceRow(int y, List<String> row) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%3d   ", y));
        for (int x = 0; x < WIDTH; x++) {
            sb.append(formatCell(row.get(x)));
            if (x != WIDTH - 1) {
                sb.append("---");
            }
        }
        System.out.println(sb);
    }

    private void printVerticalRow() {
        StringBuilder sb = new StringBuilder();
        sb.append("       ");
        for (int x = 0; x < WIDTH; x++) {
            sb.append("|");
            if (x != WIDTH - 1) {
                sb.append("      ");
            }
        }
        System.out.println(sb);
    }

    private String formatCell(String piece) {
        if (piece == null || piece.isBlank()) {
            return "[  ]";
        }
        return String.format("[%2s]", piece);
    }

    private void validateBoard(List<List<String>> board) {
        if (board == null || board.size() != HEIGHT) {
            throw new IllegalArgumentException("보드는 세로 10줄이어야 합니다.");
        }
        for (List<String> row : board) {
            if (row == null || row.size() != WIDTH) {
                throw new IllegalArgumentException("각 행은 가로 9칸이어야 합니다.");
            }
        }
    }

}
