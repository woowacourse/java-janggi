package view;

import domain.Player;
import java.util.Scanner;

public class InputView {

    private final Scanner console;

    public InputView(Scanner console) {
        this.console = console;
    }

    public int readSettingUpStrategyCommand(final Player player) {
        System.out.println(System.lineSeparator() + player.team().getName() + " 팀의 초기 전략을 선택하십시오.");
        System.out.print("""
                1. 마상상마 (Inner Elephant Setup)
                2. 상마마상 (Outer Elephant Setup)
                3. 마상마상 (Right Elephant Setup)
                4. 상마상마 (Left Elephant Setup)
                """);

        return Integer.parseInt(console.nextLine());
    }

    public String readMovingPiecePosition(final Player player) {
        System.out.println(player.team().getName() + " 플레이어의 이동할 기물의 위치를 선택해주세요. (ex. 1,1)");
        return console.nextLine();
    }

    public String readTargetPiecePosition() {
        System.out.println("기물이 도착할 위치를 입력해주세요. (ex. 1,2)");
        return console.nextLine();
    }
}
