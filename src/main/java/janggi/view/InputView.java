package janggi.view;

import java.util.Scanner;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    private InputView() {
    }

    public static String readGameMode() {
        System.out.println("[장기 게임]");
        System.out.println("1: 새 게임 시작  2: 이어하기");
        System.out.print("> ");
        return SCANNER.nextLine().strip();
    }

    public static long readGameId() {
        System.out.print("게임 번호를 입력하세요: ");
        return Long.parseLong(SCANNER.nextLine().strip());
    }

    public static String readHanSetup() {
        System.out.println("[한(漢) 차림 선택]");
        System.out.println("1: 왼상차림  2: 오른상차림  3: 안상차림  4: 바깥상차림(기본)");
        System.out.print("> ");
        return SCANNER.nextLine().trim();
    }

    public static String readChoSetup() {
        System.out.println("[초(楚) 차림 선택]");
        System.out.println("1: 왼상차림  2: 오른상차림  3: 안상차림  4: 바깥상차림(기본)");
        System.out.print("> ");
        return SCANNER.nextLine().trim();
    }

    public static String readPosition(String teamName) {
        System.out.printf("%s의 차례입니다. 이동할 좌표를 입력하세요 (예: 11 21, 종료: end)%n", toDisplayName(teamName));
        System.out.print("> ");
        return SCANNER.nextLine().trim();
    }

    private static String toDisplayName(String teamName) {
        if (teamName.equals("한")) {
            return "\u001B[1;31m" + teamName + "(漢)\u001B[0m";
        }
        return "\u001B[1;34m" + teamName + "(楚)\u001B[0m";
    }
}
