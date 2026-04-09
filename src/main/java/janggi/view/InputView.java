package janggi.view;

import janggi.domain.team.Team;

import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        System.out.println("플레이 할 게임을 선택해주세요.");
        System.out.println("(게임 ID 입력: 이어하기 | new: 새 게임 시작 | exit: 게임 종료)");
        System.out.print("> ");
        return scanner.nextLine().trim();
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

    public List<String> readPosition(Team currentTeam) {
        System.out.printf("%s의 차례입니다. 이동할 좌표를 입력하세요 (예: 11 21, 종료: end)%n", toDisplayName(currentTeam));
        System.out.print("> ");
        String input = scanner.nextLine().trim();
        List<String> positions = List.of(input.split("\\s+"));
        if (positions.size() == 1 && !positions.getFirst().equals("end")) {
            throw new IllegalArgumentException("[ERROR] 좌표 입력 형식에 맞게 입력해주세요.");
        }
        return positions;
    }

    private String toDisplayName(Team team) {
        if (team == Team.HAN) {
            return "\u001B[1;31m한(漢)\u001B[0m";
        }
        return "\u001B[1;34m초(楚)\u001B[0m";
    }
}
