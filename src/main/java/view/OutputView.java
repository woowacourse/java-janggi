package view;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

import domain.board.Board;
import domain.piece.Piece;
import domain.position.Position;

public class OutputView {

    public void printBoard(Board board) {
        printColumnHeader();
        for (int row = MIN_ROW; row < MAX_ROW; row++) {
            printPieceRow(row, board);
            printVerticalRow();
        }
        printPieceRow(MAX_ROW, board);
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
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            sb.append(column);
            if (column != MAX_COLUMN) {
                sb.append(" ---- ");
            }
        }
        System.out.println(sb);
    }

    private void printPieceRow(int row, Board board) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%3d   ", row));
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            Piece piece = board.findPiece(new Position(row, column));

            sb.append(ConsolePieceMapper.toViewString(piece.getTeam(), piece.getPieceType()));

            if (column != MAX_COLUMN) {
                sb.append("---");
            }
        }
        System.out.println(sb);
    }

    private void printVerticalRow() {
        StringBuilder sb = new StringBuilder();
        sb.append("       ");
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            sb.append("|");
            if (column != MAX_COLUMN) {
                sb.append("      ");
            }
        }
        System.out.println(sb);
    }
}