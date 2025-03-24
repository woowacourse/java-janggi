package view;

import domain.board.Board;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceColor;

public class OutputView {

    private final static String RED_COLOR = "\u001B[31m";
    private final static String BLUE_COLOR = "\u001B[34m";
    private final static String WHITE_COLOR = "\u001B[0m";
    private final static String YELLOW_COLOR = "\u001B[33m";

    public void printBorad(Board board) {
        System.out.println(YELLOW_COLOR + "  일 이 삼 사 오 육 칠 팔 구" + WHITE_COLOR);
        for (Row row : Row.values()) {
            System.out.print(YELLOW_COLOR + row.getValue() % 10 + " " + WHITE_COLOR);
            for (Column column : Column.values()) {
                Position position = new Position(row, column);
                Piece piece = board.getPieceBy(position);
                String color = applyColor(piece);
                String pieceName = PieceName.getNameFromPieceType(piece);

                System.out.print(color + pieceName + WHITE_COLOR + " ");
            }
            System.out.println();
        }
    }

    public void printTurnNotice(PieceColor turnColor) {
        String color = getTeamName(turnColor);
        System.out.println(getStringColor() + color + "차례입니다.");
    }

    public void printWinner(PieceColor turnColor) {
        String color = getTeamName(turnColor);
        System.out.println(color + " 승리");
    }

    private String applyColor(Piece piece) {
        PieceColor color = piece.getColor();
        if (color == PieceColor.RED) {
            return RED_COLOR;
        }
        if (color == PieceColor.BLUE) {
            return BLUE_COLOR;
        }
        return WHITE_COLOR;
    }

    private static String getTeamName(PieceColor turnColor) {
        if (turnColor == PieceColor.BLUE) {
            return BLUE_COLOR + "초나라 " + WHITE_COLOR;
        }
        if (turnColor == PieceColor.RED) {
            return RED_COLOR + "한나라 " + WHITE_COLOR;
        }
        return "";
    }

    public void printError(String message) {
        System.out.println(message);
    }

    private String getStringColor() {
        return "\u001B[0m";
    }
}
