package janggi.view;

import janggi.board.Point;
import janggi.camp.Camp;
import janggi.piece.Piece;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class View {

    private static final int ROW = 10;
    private static final int COLUMN = 9;
    public static final int COORDINATES_COUNT = 2;
    public static final int X_COORDINATE_INDEX = 0;
    public static final int Y_COORDINATE_INDEX = 1;
    public static final int FROM_POINT_INDEX = 0;
    public static final int TO_POINT_INDEX = 1;
    private static final String EMPTY_SPACE = "ㅤ";
    private static final String BOARD_DELIMITER_LINE = " | ";
    public static final String POINT_INPUT_DELIMITER = ",";

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

    public List<List<Integer>> readMove(Camp camp) {
        System.out.printf("%n%n[%s의 차례입니다.]%n", camp.getName());
        System.out.println("이동시킬 기물의 좌표와 도착 지점의 좌표를 입력해 주세요. 예) 03,13");
        String[] pointInput = scanner.nextLine()
                .split(POINT_INPUT_DELIMITER, -1);
        List<Integer> fromPoint = parseCoordinates(pointInput[FROM_POINT_INDEX]);
        List<Integer> toPoint = parseCoordinates(pointInput[TO_POINT_INDEX]);
        return List.of(fromPoint, toPoint);
    }

    private List<Integer> parseCoordinates(String input) {
        if (input.length() != COORDINATES_COUNT) {
            throw new IllegalArgumentException("잘못된 좌표 입력입니다.");
        }
        try {
            String[] split = input.split("", -1);
            int x = Integer.parseInt(split[X_COORDINATE_INDEX]);
            int y = Integer.parseInt(split[Y_COORDINATE_INDEX]);
            return List.of(x, y);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다.");
        }
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
