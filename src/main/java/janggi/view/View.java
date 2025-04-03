package janggi.view;

import janggi.dao.PieceDao;
import janggi.domain.board.Point;
import janggi.domain.camp.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.type.PieceType;
import java.util.Optional;
import java.util.Scanner;

public class View {

    private static final int ROW = 10;
    private static final int COLUMN = 9;
    private static final String EMPTY_SPACE = "ㅤ";
    private static final String BOARD_DELIMITER_LINE = " | ";
    private static final String ERROR_MESSAGE_FORMAT = "%n[ERROR] %s%n";

    private final Scanner scanner = new Scanner(System.in);

    public void displayStartBanner() {
        String firstTurnCampName = ColorFormatter.getColoredCampName(Camp.CHU);
        System.out.printf("""
                
                ====================================
                    Welcome to the Janggi Game!
                ====================================
                 {가로축 번호}{세로축 번호} 좌표를 사용합니다.
                    예) 41은 왼쪽에서 4번째 아래에서
                        첫번째 기물을 의미합니다.
                      게임은 %s나라부터 시작됩니다.
                ====================================%n
                """, firstTurnCampName);
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

    public String readFromPoint() {
        System.out.println("이동시킬 기물의 출발 좌표를 입력해 주세요. 예) 03");
        String input = scanner.nextLine();
        validateInput(input);
        return input.trim();
    }

    public String readToPoint() {
        System.out.println("도착 좌표를 입력해 주세요. 예) 03");
        String input = scanner.nextLine();
        validateInput(input);
        return input.trim();
    }

    public void displayBoard(PieceDao pieceDao) {
        System.out.println();
        for (int i = ROW - 1; i >= 0; i--) {
            displayRow(pieceDao, i);
        }
        System.out.print("  ");
        for (int i = 0; i < COLUMN; i++) {
            System.out.printf(EMPTY_SPACE + " %d ", i);
        }
        System.out.println();
    }

    private void displayRow(PieceDao pieceDao, int i) {
        System.out.print(i);
        for (int j = 0; j < COLUMN; j++) {
            displayPiece(pieceDao, i, j);
        }
        System.out.println(BOARD_DELIMITER_LINE);
    }

    private void displayPiece(PieceDao pieceDao, int i, int j) {
        Point point = new Point(j, i);
        Optional<Piece> piece = pieceDao.findByPoint(point);

        if (!piece.isPresent()) {
            System.out.print(BOARD_DELIMITER_LINE + EMPTY_SPACE);
            return;
        }
        System.out.print(BOARD_DELIMITER_LINE + formatPiece(piece.get()));
    }


    private String formatPiece(Piece piece) {
        PieceType pieceType = piece.getPieceType();
        return ColorFormatter.getColoredPieceAttributes(piece.getCamp(), pieceType);
    }

    private void validateInput(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("입력값이 비어있습니다.");
        }
    }

    public void displayErrorMessage(String errorMessage) {
        System.out.printf(ERROR_MESSAGE_FORMAT, errorMessage);
    }

    public void displayEndingMessage(Camp camp) {
        String winningCampName = ColorFormatter.getColoredCampName(camp);
        String losingCampName = ColorFormatter.getColoredCampName(camp.reverse());
        System.out.printf("%n%s의 장군이 쓰러졌습니다. %s의 승리입니다.%n", losingCampName, winningCampName);
    }

    public void displayScore(Camp camp, double score) {
        System.out.printf("%s나라의 점수는 %.1f점 입니다.%n", ColorFormatter.getColoredCampName(camp), score);
    }

    public String readGameCommand(Camp camp) {
        System.out.printf("%n[%s의 차례입니다.]%n", ColorFormatter.getColoredCampName(camp));
        System.out.printf("%n기물을 이동하려면 move, 게임을 종료하려면 end를 입력해주세요.%n");
        String input = scanner.nextLine();
        if (!input.equals("move") && !input.equals("end")) {
            throw new IllegalArgumentException("move 또는 end를 입력해야 합니다.");
        }
        return input;
    }
}
