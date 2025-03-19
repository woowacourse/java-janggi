package view;

import domain.board.Board;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;

import java.util.Scanner;

public class OutputView {
    private final Scanner scanner = new Scanner(System.in);

    public void printBorad(Board board) {
        for(Row row : Row.values()) {
            for(Column column : Column.values()) {
                Position position = new Position(row, column);
                Piece piece = board.getPieceBy(position);
                String pieceName = PieceName.getNameFromPiece(piece);

                System.out.print(pieceName + " ");
            }
            System.out.println();
        }
    }
}
