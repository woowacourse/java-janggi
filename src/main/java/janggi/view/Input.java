package janggi.view;

import janggi.team.Team;

import java.util.Scanner;

public class Input {
    private final Scanner scanner = new Scanner(System.in);

    public String readPositionOption(Team team) {
        System.out.printf("%s의 상차림을 선택해주세요. [입력 예시 : 상마상마, 마상마상, 마상상마, 상마마상]%n", team.getValue());
        return scanner.nextLine();
    }

    // 이후 팀 턴, 말의 생사 구현 필요
    public String readPieceTeamName() {
        System.out.println("움직임 팀 이름을 입력해주세요.");
        System.out.println("ex) 초");
        return scanner.nextLine();
    }

    public String readPieceStartPoint() {
        System.out.println("움직일 기물 이름과 출발 좌표를 입력해주세요.");
        System.out.println("ex) E-[1, 0]"); //출발 좌표가 해당 기물의 종류가 맞는지 검증 필요
        return scanner.nextLine();
    }

    public String readPieceDestination() {
        System.out.println("움직일 기물의 도착 좌표를 입력해주세요.");
        System.out.println("ex) [3, 3]"); //도착 좌표가 도달할 수 있는 곳인지 검증 필요
        return scanner.nextLine();
    }
}
