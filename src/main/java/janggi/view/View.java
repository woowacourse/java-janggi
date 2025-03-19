package janggi.view;

import janggi.PieceSymbol;
import janggi.Point;
import janggi.piece.Piece;
import java.util.Map;
import java.util.Scanner;

public class View {

    private static final int ROW = 10;
    private static final int COLUMN = 9;
    private static final String EMPTY_SPACE = "ㅤ";
    private final Scanner scanner = new Scanner(System.in);

    public boolean readStartGame() {
        System.out.println("게임을 시작하시겠습니까? (y/n)");
        String response = scanner.nextLine();
        return parseYesOrNo(response);
    }

    private boolean parseYesOrNo(String response) {
        if (response.equalsIgnoreCase("y")) {
            return true;
        }
        if (response.equalsIgnoreCase("n")) {
            return false;
        }
        throw new IllegalArgumentException("y 또는 n을 입력해야 합니다.");
    }

    public void displayBoard(Map<Point, Piece> placedPieces) {
        for (int i = ROW; i >= 1; i--) {
            System.out.printf("%2d", i);
            for (int j = COLUMN; j >= 1; j--) {
                if (placedPieces.get(new Point(j, i)) == null) {
                    System.out.print(" | " + EMPTY_SPACE);
                    continue;
                }
                System.out.print(" | " + formatPiece(placedPieces.get(new Point(j, i))));
            }
            System.out.println(" | ");
        }

        System.out.print("   ");
        for (int i = 1; i <= COLUMN; i++) {
            System.out.printf("ㅤ %d ", i);
        }
    }

    private String formatPiece(Piece piece) {
        PieceSymbol pieceSymbol = piece.getPieceSymbol();
        return pieceSymbol.getDisplayAttributes(piece.getCamp());
    }
}
