package janggi.view;

import janggi.domain.board.Board;
import janggi.domain.board.Column;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceColor;

public class OutputView {

    public void printBoard(Board board) {
        StringBuilder sb = new StringBuilder();

        sb.append(getBoardHeader());
        for(Row row : Row.values()) {
            sb.append(String.format(getStringColor() + "%d | ", row.getValue()));
            for (Column column : Column.values()) {
                Position position = new Position(row, column);
                Piece piece = board.getPieceBy(position);
                String pieceName = PieceTypeName.getNameFrom(piece);

                sb.append(pieceName + " ");
            }
            sb.append(System.lineSeparator());
        }
        System.out.println(sb);
    }

    private String getBoardHeader() {
        return "  | 1  2 3  4 5  6 7  8 9\n"
                + "--|----------------------\n";
    }

    public void printTurnNotice(PieceColor turnColor) {
        String teamName = TeamColorName.geNameFrom(turnColor);
        System.out.printf(getStringColor() + "%s 차례입니다\n", teamName);
    }

    public void printWinner(PieceColor turnColor) {
        String teamName = TeamColorName.geNameFrom(turnColor);
        System.out.printf(getStringColor() + "%s 승리!\n", teamName);
    }

    private String getStringColor() {
        return "\u001B[0m";
    }
}
