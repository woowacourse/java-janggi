package janggi.view;

import janggi.board.point.Point;
import janggi.piece.Camp;
import janggi.piece.Piece;
import janggi.piece.PieceSymbol;
import java.util.Map;
import java.util.Scanner;

public final class View {

    private static final int ROW = 10;
    private static final int COLUMN = 9;
    private static final String EMPTY_SPACE = "ㅤ";
    private static final String BOARD_DELIMITER_LINE = " | ";
    private static final String ERROR_MESSAGE_FORMAT = "%n[ERROR] %s";

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

    public void displayCurrentTurnCamp(Camp camp) {
        System.out.printf("%n%n[%s의 차례입니다.]%n", CampFormatter.format(camp));
    }

    public String readMoveFromPoint() {
        System.out.println("이동시킬 기물의 좌표를 입력해 주세요. 예) 03");
        return scanner.nextLine();
    }

    public String readMoveToPoint() {
        System.out.println("기물을 이동할 위치의 좌표를 입력해 주세요. 예) 13");
        return scanner.nextLine();
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
        if (!placedPieces.containsKey(new Point(j, i))) {
            System.out.print(BOARD_DELIMITER_LINE + EMPTY_SPACE);
            return;
        }
        System.out.print(BOARD_DELIMITER_LINE + formatPiece(placedPieces.get(new Point(j, i))));
    }

    private String formatPiece(Piece piece) {
        PieceSymbol pieceSymbol = piece.getPieceSymbol();
        return PieceFormatter.formatPiece(pieceSymbol, piece.getCamp());
    }

    public void displayErrorMessage(String message) {
        System.out.printf((ERROR_MESSAGE_FORMAT), message);
    }
}
