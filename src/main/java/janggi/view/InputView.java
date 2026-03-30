package janggi.view;

import java.util.Scanner;

public class InputView {

    public static final Scanner scanner = new Scanner(System.in);

    public static String askFromPosition() {
        System.out.println("옮길 기물의 현재 좌표를 x,y 형식으로 입력해주세요.");
        return scanner.nextLine();
    }

    public static String askToPosition() {
        System.out.println("옮길 기물의 도착 좌표를 x,y 형식으로 입력해주세요.");
        return scanner.nextLine();
    }
}
