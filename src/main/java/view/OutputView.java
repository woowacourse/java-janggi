package view;

import domain.board.Board;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceColor;

public class OutputView {
    public void printBorad(Board board) {
        System.out.println("  일 이 삼 사 오 육 칠 팔 구");
        for (Row row : Row.values()) {
            System.out.print(row.getValue() % 10 + " ");
            for (Column column : Column.values()) {
                Position position = new Position(row, column);
                Piece piece = board.getPieceBy(position);
                String pieceName = PieceName.getNameFromPieceType(piece);

                System.out.print(pieceName + " ");
            }
            System.out.println();
        }
    }

    public void printTurnNotice(PieceColor turnColor) {
        String color = "";
        if (turnColor == PieceColor.BLUE) {
            color = "초나라";
        }
        if (turnColor == PieceColor.RED) {
            color = "한나라";
        }
        System.out.println(getStringColor() + color + "차례입니다.");
    }

    public void printWinner(PieceColor turnColor) {
        String color = "";
        if (turnColor == PieceColor.BLUE) {
            color = "초나라";
        }
        if (turnColor == PieceColor.RED) {
            color = "한나라";
        }
        System.out.println(color + " 승리");
    }

    public void printError(String message) {
        System.out.println(message);
    }

    private String getStringColor() {
        return "\u001B[0m";
    }
}
