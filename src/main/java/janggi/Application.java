package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.piece.Piece;
import janggi.view.View;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        View view = new View();
        boolean startGame = view.readStartGame();
        if (startGame) {
            Board board = BoardGenerator.generate();
            Map<Point, Piece> placedPieces = board.getPlacedPieces();
            view.displayBoard(placedPieces);

            Camp camp = Camp.CHU;
            while (true) {
                String[] input = view.readMove(camp);
                Point from = new Point(input[0]);
                Point to = new Point(input[1]);
                board.move(from, to, camp);
                view.displayBoard(board.getPlacedPieces());
                camp = camp.reverse();
            }
        }
    }
}
