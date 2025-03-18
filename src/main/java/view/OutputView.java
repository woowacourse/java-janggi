package view;

import domain.Board;
import domain.Player;
import domain.piece.Piece;
import domain.piece.Pieces;
import domain.piece.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputView {

    public void printBoard(final Board board) {
        Map<Player, Pieces> boardElement = board.getBoard();
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> e = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                e.add("-");
            }
            result.add(e);
        }

        for (Player player : boardElement.keySet()) {
            Pieces pieces = boardElement.get(player);

            List<Piece> pieceElements = pieces.getPieces();
            for (Piece pieceElement : pieceElements) {
                Position position = pieceElement.getPosition();

                int row = position.getRow() - 1;
                int column = position.getColumn() - 1;

                List<String> rows = result.get(column);
                rows.set(row, pieceElement.getName());
                result.set(column, rows);
            }
        }

        for (List<String> strings : result) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
    }
}
