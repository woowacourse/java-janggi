package view;

import domain.Team;

import java.util.Scanner;

public class InputView {

    final Scanner scanner = new Scanner(System.in);

    public String readPosition() {
        System.out.println("움직일 기물의 위치를 입력해주세요. (예: 0 0)");
        return scanner.nextLine();
    }

    public String readTargetPosition() {
        System.out.println("기물을 움직일 위치를 입력해주세요. (예: 0 0)");
        return scanner.nextLine();
    }

    public String readArrangement(final Team team) {
        System.out.println(team.getName() + "나라의 판차림 방식을 선택해주세요.(상마상마, 마상마상, 마상상마, 상마마상)");
        return scanner.nextLine();
    }

    public String readCommand() {
        System.out.println("1. 새 게임 2. 이어하기");
        return scanner.nextLine();
    }

    public String readGameId() {
        System.out.println("이어서 진행할 게임 id를 입력해주세요.");
        return scanner.nextLine();
    }
}
