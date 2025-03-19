package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.piece.Piece;
import janggi.view.View;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        Board board = BoardGenerator.generate();
        Map<Point, Piece> placedPieces = board.getPlacedPieces();
        View view = new View();
        view.displayBoard(placedPieces);
    }
}
