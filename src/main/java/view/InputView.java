package view;

import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readDeparturePosition() {
        System.out.println("이동시킬 기물이 위치한 좌표를 입력해주세요. (ex) 0,0");
        return scanner.nextLine();
    }

    public static String readDestinationPosition() {
        System.out.println("이동할 좌표를 입력해주세요. (ex) 0,0");
        return scanner.nextLine();
    }
}
