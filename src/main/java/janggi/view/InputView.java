package janggi.view;

import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public String readHanSetup() {
        System.out.println("[한(漢) 차림 선택]");
        System.out.println("1: 왼상차림  2: 오른상차림  3: 안상차림  4: 바깥상차림(기본)");
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    public String readChoSetup() {
        System.out.println("[초(楚) 차림 선택]");
        System.out.println("1: 왼상차림  2: 오른상차림  3: 안상차림  4: 바깥상차림(기본)");
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    public List<String> readPosition(String teamName) {
        System.out.printf("%s의 차례입니다. 이동할 좌표를 입력하세요 (예: 11 21, 종료: end)%n", toDisplayName(teamName));
        System.out.print("> ");
        String input = scanner.nextLine().trim();

        return List.of(input.split("\\s+"));
    }

    private String toDisplayName(String teamName) {
        if (teamName.equals("한")) {
            return "\u001B[1;31m" + teamName + "(漢)\u001B[0m";
        }
        return "\u001B[1;34m" + teamName + "(楚)\u001B[0m";
    }
}
