package view;

import dto.PieceInfo;
import java.util.Scanner;

public class InputView {

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    private static Scanner scanner = new Scanner(System.in);

    public static String selectPiecePosition() {
        System.out.println("이동시킬 기물을 선택해주세요.");
        return scanner.nextLine();
    }

    public static String selectTargetPositionOf(PieceInfo pieceInfo) {
        String message = String.format("선택한 %s 기물을 이동시킬 위치를 선택해주세요.", colorize(pieceInfo));
        System.out.println(message);

        return scanner.nextLine();
    }

    private static String colorize(PieceInfo piece) {
        String info = String.format("%s(%d, %d)", piece.name(), piece.row(), piece.col());
        if (piece.isGreenTeam()) {
            return GREEN + info + RESET;
        }

        if (piece.isRedTeam()) {
            return RED + info + RESET;
        }
        return info;
    }
}
