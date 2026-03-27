package view;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

import domain.board.Board;
import domain.piece.Piece;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class OutputView {
    private static final int WIDTH = 9;
    private static final int HEIGHT = 10;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public List<List<String>> createStringBoard(Board board) {
        List<List<String>> stringBoard = new ArrayList<>();

        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            List<String> lineOfStringBoard = makeLineOfStringBoard(row, board);
            stringBoard.add(lineOfStringBoard);
        }

        return stringBoard;
    }

    public void printBoard(Board board) {
//        validateBoard(board);
//        System.out.println();
        List<List<String>> stringBoard = createStringBoard(board);
        printColumnHeader();
        for (int y = 0; y < 9; y++) {
            printPieceRow(y, stringBoard.get(y));
            printVerticalRow();
        }
        printPieceRow(9, stringBoard.get(9));
        System.out.println();
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printPlayerTurnMessage(String name, String team) {
        System.out.println(name + "(" + team + ")" + "님의 차례입니다.");
    }

    private void printColumnHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("       ");
        for (int x = 0; x < WIDTH; x++) {
            sb.append(x);
            if (x != WIDTH - 1) {
                sb.append(" ---- ");
            }
        }
        System.out.println(sb);
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
        char teamCode = piece.charAt(0);
        String pieceString = piece.substring(1);
        if (teamCode == 'C') {
            return String.format("[%s%2s%s]", ANSI_BLUE, pieceString, ANSI_RESET);
        }
        if (teamCode == 'H') {
            return String.format("[%s%2s%s]", ANSI_RED, pieceString, ANSI_RESET);
        }
        return String.format("[%2s]", pieceString);

    }
//
//    private void validateBoard(List<List<String>> board) {
//        if (board == null || board.size() != HEIGHT) {
//            throw new IllegalArgumentException("보드는 세로 10줄이어야 합니다.");
//        }
//        for (List<String> row : board) {
//            if (row == null || row.size() != WIDTH) {
//                throw new IllegalArgumentException("각 행은 가로 9칸이어야 합니다.");
//            }
//        }
//    }

    private List<String> makeLineOfStringBoard(int row, Board board) {
        List<String> lineOfStringBoard = new ArrayList<>();

        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            Piece piece = board.findPiece(new Position(row, column));
            String pieceString = piece.getPieceString();
            String teamString = piece.getTeamString();
            lineOfStringBoard.add(teamString + pieceString);
        }

        return lineOfStringBoard;
    }
}
