package view;

import domain.Player;
import java.util.Scanner;

public class InputView {

    private final Scanner console;

    public InputView(Scanner console) {
        this.console = console;
    }

    public boolean readGameSelection() {
        System.out.println();
        System.out.println("""
                진행 중인 게임이 존재합니다.
                1. 진행 중인 게임 선택
                2. 새로운 게임 만들기
                번호를 선택해 입력해주세요.""");
        String command = console.nextLine();
        return command.equals("1");
    }

    public String readCreateGameName() {
        System.out.println();
        System.out.println("생성할 게임 이름을 입력하세요.");
        return console.nextLine();
    }

    public String readGameName() {
        System.out.println();
        System.out.println("진행할 게임 이름을 입력하세요.");
        return console.nextLine();
    }

    public int readSettingUpStrategyCommand(final Player player) {
        System.out.println(System.lineSeparator() + player.getTeam().getName() + " 팀의 초기 전략을 선택하십시오.");
        System.out.print("""
                1. 마상상마 (Inner Elephant Setup)
                2. 상마마상 (Outer Elephant Setup)
                3. 마상마상 (Right Elephant Setup)
                4. 상마상마 (Left Elephant Setup)
                """);

        return Integer.parseInt(console.nextLine());
    }

    public String readMovingPiecePosition(final Player player) {
        System.out.println(player.getTeam().getName() + " 플레이어의 이동할 기물의 위치를 선택해주세요. (ex. 1,1)");
        return console.nextLine();
    }

    public String readTargetPiecePosition() {
        System.out.println("기물이 도착할 위치를 입력해주세요. (ex. 1,2)");
        return console.nextLine();
    }

    public boolean isGameTurnEnd() {
        System.out.println("한 턴을 시작하려면 아무런 키를 입력하세요. [중단 : N]");
        String command = console.nextLine().toUpperCase();
        System.out.println();
        return command.equals("N");
    }
}
