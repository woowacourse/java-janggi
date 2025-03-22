package janggi.view;

import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Column;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceColor;

public class OutputView {
    public static final String WHITE_COLOR = "\u001B[0m";
    public static final String RED_COLOR = "\u001B[31m";
    public static final String BLUE_COLOR = "\u001B[34m";

    public void printBoard(PlayingBoard playingBoard) {
        StringBuilder sb = new StringBuilder();

        sb.append(getBoardHeader());
        for(Row row : Row.values()) {
            sb.append(String.format(getDefaultColor() + "%d | ", row.getValue()));
            for (Column column : Column.values()) {
                Position position = new Position(row, column);
                Piece piece = playingBoard.getPieceBy(position);
                String color = getColorFrom(piece);
                String pieceName = PieceTypeName.getNameFrom(piece);

                sb.append(color + pieceName + " ");
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
        String teamName = TeamColorName.getNameFrom(turnColor);
        System.out.printf(getDefaultColor() + "%s 차례입니다\n", teamName);
    }

    public void printWinner(PieceColor turnColor) {
        String teamName = TeamColorName.getNameFrom(turnColor);
        System.out.printf(getDefaultColor() + "%s 승리!\n", teamName);
    }

    private String getDefaultColor() {
        return WHITE_COLOR;
    }

    private String getColorFrom(Piece piece) {
        PieceColor color = piece.getColor();
        if(color == PieceColor.RED) {
            return RED_COLOR;
        }
        if(color == PieceColor.BLUE) {
            return BLUE_COLOR;
        }
        return WHITE_COLOR;
    }
}
