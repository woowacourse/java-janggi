package view;

import board.Board;
import java.util.Map;
import pieces.Cha;
import pieces.Gung;
import pieces.JolByeong;
import pieces.Ma;
import pieces.Piece;
import pieces.Po;
import pieces.Sa;
import pieces.Sang;
import pieces.Side;
import position.Position;

public class OutputView {
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    public void printSangSetupType(Side side) {
        System.out.printf("%s의 상차림을 선택하세요.%n", sideName(side));
        System.out.println("1. 왼상차림(象馬象馬, 상마상마)");
        System.out.println("2. 오른상차림(馬象馬象, 마상마상)");
        System.out.println("3. 안상차림(馬象象馬, 마상상마)");
        System.out.println("4. 바깥상차림(象馬馬象, 상마마상)");
    }

    public void printTurn(Side side) {
        System.out.printf("현재 턴: %s%n", side.isCho() ? "초" : "한");
    }

    public void printMoveGuide() {
        System.out.println("이동할 기물의 출발지와 도착지를 입력하세요.");
    }

    public void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printBoard(Board board) {
        Map<Position, Piece> pieces = board.pieces();

        for (int row = 9; row >= 0; row--) {
            System.out.printf("%2d ", row);
            for (int column = 0; column <= 8; column++) {
                Piece piece = pieces.get(new Position(row, column));
                System.out.print("|" + displayPiece(piece));
            }
            System.out.println("|");
            System.out.println("   ---------------------------------------------");
        }
        System.out.println("     0    1    2    3    4    5    6    7    8");
    }

    private String sideName(Side side) {
        if (side.isCho()) {
            return "초나라";
        }
        return "한나라";
    }

    private String displayPiece(Piece piece) {
        String text = pieceText(piece);
        String padded = String.format(" %-3s", text);

        if (piece.isCho()) {
            return GREEN + padded + RESET;
        }
        if (piece.isHan()) {
            return RED + padded + RESET;
        }
        return padded;
    }

    private String pieceText(Piece piece) {
        if (piece.isEmpty()) {
            return ".";
        }
        if (piece instanceof Cha) {
            return "차";
        }
        if (piece instanceof Ma) {
            return "마";
        }
        if (piece instanceof Sang) {
            return "상";
        }
        if (piece instanceof Sa) {
            return "사";
        }
        if (piece instanceof Gung) {
            return "궁";
        }
        if (piece instanceof Po) {
            return "포";
        }
        if (piece instanceof JolByeong) {
            return "졸";
        }
        throw new IllegalArgumentException("알 수 없는 기물입니다.");
    }
}
