package janggi.view;

import janggi.team.Team;

import java.util.Scanner;

public class Input {
    private final Scanner scanner = new Scanner(System.in);
    // 사용자가 움직일 말 이름과, 시작 좌표, 도착 좌표를 입력한다
    public String readPieceNameAndLocation() {
        System.out.println("움직일 말 이름과 출발 좌표, 도착 좌표를 입력해주세요 (,로 구분하여 입력)");
        return scanner.nextLine();
    }

    public String readPositionOption(Team team) {
        System.out.printf("%s의 상차림을 선택해주세요. [입력 예시 : 상마상마, 마상마상, 마상상마, 상마마상]%n", team.getValue());
        return scanner.nextLine();
    }
}
