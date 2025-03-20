package view;

import board.GameBoard;
import direction.Point;
import java.util.Optional;
import piece.Piece;
import piece.Pieces;

public class OutputView {

    public static void displayBoard(GameBoard gameBoard) {
        Pieces pieces = gameBoard.findAllPieces();

        System.out.println();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                Point point = new Point(j, i);
                Optional<Piece> findPiece = pieces.getPieces().stream()
                        .filter(piece -> piece.getPosition().equals(point))
                        .findAny();
                if(findPiece.isEmpty()) {
                    System.out.print(".");
                    continue;
                }
                System.out.print(findPiece.get().getName());
            }
            System.out.println(" " + i);
        }
        System.out.println("123456789");
        System.out.println();
    }

    public static void displayWrongPoint() {
        System.out.println("본인의 기물이 아닙니다. 다시 선택해 주세요.");
    }
}
