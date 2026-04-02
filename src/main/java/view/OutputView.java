package view;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

import domain.board.Board;
import domain.piece.BasicPiece;
import domain.piece.Piece;
import domain.player.PlayerProfile;
import domain.position.Position;

public class OutputView {

    private static final String COLUMN_GAP = " ---- ";
    private static final String VERTICAL_LINE = "|";
    private static final String DEFAULT_VERTICAL_GAP = "      ";

    public void printBoard(Board board) {
        printColumnHeader();

        for (int row = MIN_ROW; row < MAX_ROW; row++) {
            printPieceRow(row, board);
            printVerticalRow(row);
        }
        printPieceRow(MAX_ROW, board);
        System.out.println();
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printPlayerTurnMessage(PlayerProfile profile) {
        System.out.println(profile.nameValue() + "(" + profile.team() + ") 님의 차례입니다.");
    }

    private void printColumnHeader() {
        StringBuilder sb = new StringBuilder("       ");
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            sb.append(column);
            if (column != MAX_COLUMN) {
                sb.append(COLUMN_GAP);
            }
        }
        System.out.println(sb);
    }

    private void printPieceRow(int row, Board board) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%3d   ", row));
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            BasicPiece piece = board.findPiece(new Position(row, column));
            sb.append(ConsolePieceMapper.toViewString(piece));

            if (column != MAX_COLUMN) {
                sb.append("---");
            }
        }
        System.out.println(sb);
    }

    private void printVerticalRow(int row) {
        StringBuilder sb = new StringBuilder("       ");
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            sb.append(VERTICAL_LINE);
            if (column != MAX_COLUMN) {
                sb.append(getVerticalGap(row, column));
            }
        }
        System.out.println(sb);
    }

    private String getVerticalGap(int row, int col) {
        if (row == 0 && col == 3) return "  \\   ";
        if (row == 0 && col == 4) return "   /  ";
        if (row == 1 && col == 3) return "  /   ";
        if (row == 1 && col == 4) return "   \\  ";

        if (row == 7 && col == 3) return "  \\   ";
        if (row == 7 && col == 4) return "   /  ";
        if (row == 8 && col == 3) return "  /   ";
        if (row == 8 && col == 4) return "   \\  ";

        return DEFAULT_VERTICAL_GAP;
    }
}