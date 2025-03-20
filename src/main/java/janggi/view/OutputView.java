package janggi.view;

import janggi.domain.board.Board;
import janggi.domain.board.Column;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceColor;

public class OutputView {

    public void printBoard(Board board) {
        for(Row row : Row.values()) {
            for(Column column : Column.values()) {
                Position position = new Position(row, column);
                Piece piece = board.getPieceBy(position);
                String pieceName = PieceTypeName.getNameFrom(piece);

                System.out.print(pieceName + " ");
            }
            System.out.println();
        }
    }

    public void printTurnNotice(PieceColor turnColor) {
        String color = "";
        if(turnColor == PieceColor.BLUE){
            color = "초나라";
        }
        if(turnColor == PieceColor.RED){
            color = "한나라";
        }
        System.out.println(getStringColor() + color + "차례입니다.");
    }

    public void printWinner(PieceColor turnColor) {
        String color = "";
        if(turnColor == PieceColor.BLUE){
            color = "초나라";
        }
        if(turnColor == PieceColor.RED){
            color = "한나라";
        }
        System.out.println(color + " 승리");
    }

    private String getStringColor() {
        return "\u001B[0m";
    }
}
