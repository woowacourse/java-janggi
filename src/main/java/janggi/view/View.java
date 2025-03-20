package janggi.view;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;
import janggi.piece.Piece;
import java.util.Map;
import java.util.Scanner;

public class View {

    private static final int ROW = 10;
    private static final int COLUMN = 9;
    private static final String EMPTY_SPACE = "ㅤ";
    private static final String BOARD_DELIMITER_LINE = " | ";

    private final Scanner scanner = new Scanner(System.in);

    public void displayStartBanner() {
        System.out.println("""
                
                ====================================
                    Welcome to the Janggi Game!
                ====================================
                 {가로축 번호}{세로축 번호} 좌표를 사용합니다.
                    예) 41은 왼쪽에서 4번째 아래에서
                        첫번째 기물을 의미합니다.
                      게임은 초나라부터 시작됩니다.
                ====================================
                """);
    }

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

    public String[] readMove(Camp camp) {
        System.out.printf("%n%n[%s의 차례입니다.]%n", camp.getName());
        System.out.println("이동시킬 기물의 좌표와 도착 지점의 좌표를 입력해 주세요. 예) 03,13");
        return scanner.nextLine()
                .split(",", -1);
    }

    public void displayBoard(Map<Point, Piece> placedPieces) {
        System.out.println();
        for (int i = ROW - 1; i >= 0; i--) {
            displayRow(placedPieces, i);
        }
        System.out.print("  ");
        for (int i = 0; i < COLUMN; i++) {
            System.out.printf(EMPTY_SPACE + " %d ", i);
        }
    }

    private void displayRow(Map<Point, Piece> placedPieces, int i) {
        System.out.print(i);
        for (int j = 0; j < COLUMN; j++) {
            displayPiece(placedPieces, i, j);
        }
        System.out.println(BOARD_DELIMITER_LINE);
    }

    private void displayPiece(Map<Point, Piece> placedPieces, int i, int j) {
        if (placedPieces.get(new Point(j, i)) == null) {
            System.out.print(BOARD_DELIMITER_LINE + EMPTY_SPACE);
            return;
        }
        System.out.print(BOARD_DELIMITER_LINE + formatPiece(placedPieces.get(new Point(j, i))));
    }

    private String formatPiece(Piece piece) {
        PieceSymbol pieceSymbol = piece.getPieceSymbol();
        return pieceSymbol.getDisplayAttributes(piece.getCamp());
    }
}
