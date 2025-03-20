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

            Camp baseCamp = Camp.CHU;
            while (true) {
                String[] input = view.readMove(baseCamp);
                Point from = new Point(input[0]);
                Point to = new Point(input[1]);
                validateSelectedPiece(board, from, baseCamp);
                board.move(from, to);
                view.displayBoard(board.getPlacedPieces());
                baseCamp = baseCamp.reverse();
            }
        }
    }

    private static void validateSelectedPiece(Board board, Point from, Camp baseCamp) {
        Piece piece = board.peek(from);
        piece.validateSelect(baseCamp);
    }
}
