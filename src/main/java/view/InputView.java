package view;

import dto.PieceInfo;
import dto.SelectLoadGameRequest;
import dto.SelectPositionRequest;
import java.util.Scanner;

public final class InputView {

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    private static final Scanner scanner = new Scanner(System.in);

    private InputView() {

    }

    public static SelectLoadGameRequest selectLoadUnfinishedGame() {
        System.out.println("이전에 진행중이던 게임이 존재합니다. 이어하시겠습니까?.");
        return SelectLoadGameRequest.of(readLine());
    }

    public static SelectPositionRequest selectPiecePosition() {
        System.out.println("이동시킬 기물을 선택해주세요.");
        return SelectPositionRequest.of(readLine());
    }

    public static SelectPositionRequest selectTargetPositionWith(PieceInfo pieceInfo) {
        String message = String.format("선택한 %s 기물을 이동시킬 위치를 선택해주세요.", colorize(pieceInfo));
        System.out.println(message);

        return SelectPositionRequest.of(readLine());
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

    private static String readLine() {
        return scanner.nextLine();
    }
}
