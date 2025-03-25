package view;

import domain.Player;
import domain.SetUp;
import java.util.Scanner;

public class InputView {

    private final Scanner console;

    public InputView(Scanner console) {
        this.console = console;
    }

    public String readMovingPiecePosition(final Player player) {
        System.out.println(player.name() + " 플레이어의 이동할 기물의 위치를 선택해주세요. (ex. 1,1)");
        return console.nextLine();
    }

    public String readTargetPiecePosition() {
        System.out.println("기물이 도착할 위치를 입력해주세요. (ex. 1,2)");
        return console.nextLine();
    }

    public SetUp readSetUp() {
        System.out.println("기물 배치 전략을 선택해주세요.");
        System.out.println("1. 안상 차림");
        System.out.println("2. 바깥상 차림");
        System.out.println("3. 오른상 차림");
        System.out.println("4. 왼상 차림");
        return SetUp.getValue(console.nextLine());
    }
}
