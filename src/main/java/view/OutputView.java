package view;

import controller.response.BoardView;
import domain.board.ElephantSetup;
import domain.piece.Piece;
import domain.piece.Position;
import domain.player.Team;
import java.util.List;

public class OutputView {

    private static final String EMPTY = "    ";
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String BOARD_OUTER_SPACES = "   ";


    public void printEnterChoPlayerNamePrompt() {
        System.out.println("초나라 플레이어의 이름을 입력하세요(2~5자, 영어만 사용):");
    }

    public void printEnterHanPlayerNamePrompt() {
        System.out.println("한나라 플레이어의 이름을 입력하세요(2~5자, 영어만 사용):");
    }


    public void printChoiceChoElephantSetupPrompt() {
        System.out.println("초나라 플레이어가 사용할 상차림 번호를 입력하세요 ");
        printElephantSetups();
    }

    public void printChoiceHanElephantSetupPrompt() {
        System.out.println("한나라 플레이어가 사용할 상차림 번호를 입력하세요 ");
        printElephantSetups();
    }


    public void printBoard(BoardView board) {
        printHorizontal(board);

        for (int row = board.minRow(); row <= board.maxRow(); row++) {
            printRow(board, row);
            printHorizontal(board);
        }

        printColumnNumbers(board);
    }

    private void printRow(BoardView board, int row) {
        System.out.printf("%2d ", row);
        System.out.print("|");

        for (int col = board.minCol(); col <= board.maxCol(); col++) {
            Position pos = Position.of(row, col);
            System.out.print(renderCell(board, pos));
            System.out.print("|");
        }

        System.out.println();
    }

    private String renderCell(BoardView board, Position pos) {
        return board.findPiece(pos)
                .map(this::renderPiece)
                .orElse(EMPTY);
    }

    private String renderPiece(Piece piece) {
        String name = piece.getPieceType().getNameOf(piece.getTeam());
        String centered = " " + name + " ";

        if (piece.getTeam() == Team.HAN) {
            return RED + centered + RESET;
        }
        return BLUE + centered + RESET;
    }

    private void printHorizontal(BoardView board) {
        int cols = board.maxCol() - board.minCol() + 1;

        StringBuilder sb = new StringBuilder();

        sb.append(BOARD_OUTER_SPACES);
        sb.append("+----".repeat(Math.max(0, cols)));
        sb.append("+");

        System.out.println(sb);
    }

    private void printColumnNumbers(BoardView board) {
        System.out.print(BOARD_OUTER_SPACES);

        for (int col = board.minCol(); col <= board.maxCol(); col++) {
            System.out.printf("  %2d ", col);
        }

        System.out.println();
    }


    private void printElephantSetups() {
        StringBuilder promptBuilder = new StringBuilder();
        List<String> descriptions = ElephantSetup.descriptions();
        for (int i = 0; i < descriptions.size(); i++) {
            promptBuilder.append(String.format("%d. %s ", i + 1, descriptions.get(i)));
        }
        System.out.println(promptBuilder);
    }
}
