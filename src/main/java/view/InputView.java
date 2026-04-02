package view;

import domain.board.BoardSetting;

import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public static BoardSetting readBoardSetting() {
        System.out.println("장기판 상차림을 선택해주세요.");
        System.out.println("1. 왼상차림  2. 오른상차림  3. 바깥상차림  4. 안상차림");
        return BoardSetting.from(scanner.nextLine());
    }

    public static String readDeparturePosition() {
        System.out.println("이동시킬 기물이 위치한 좌표를 입력해주세요. (ex) 0,0");
        return scanner.nextLine();
    }

    public static String readDestinationPosition() {
        System.out.println("이동할 좌표를 입력해주세요. (ex) 0,0");
        return scanner.nextLine();
    }
}
