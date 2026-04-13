package janggi.view;

import java.util.Scanner;

public class InputView {

    public static final Scanner scanner = new Scanner(System.in);

    public static String askGameMode() {
        System.out.println("새로운 게임을 시작할 시에는 new, 이전 게임을 불러오기 위해서는 load를 입력해주세요.");
        return scanner.nextLine();
    }

    public static String askGameId() {
        System.out.println("불러올 게임의 id를 입력해주세요.");
        return scanner.nextLine();
    }

    public static String askFromPosition() {
        System.out.println("옮길 기물의 현재 좌표를 x,y 형식으로 입력해주세요.");
        return scanner.nextLine();
    }

    public static String askToPosition() {
        System.out.println("옮길 기물의 도착 좌표를 x,y 형식으로 입력해주세요.");
        return scanner.nextLine();
    }
}
