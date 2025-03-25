package view;

import domain.Player;
import domain.SetUp;
import domain.piece.Position;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner console;

    public InputView(Scanner console) {
        this.console = console;
    }

    public Position readMovingPiecePosition(final Player player) {
        System.out.println(player.name() + " 플레이어의 이동할 기물의 위치를 선택해주세요. (ex. 1,1)");
        return parseToPosition(console.nextLine());
    }

    public Position readTargetPiecePosition() {
        System.out.println("기물이 도착할 위치를 입력해주세요. (ex. 1,2)");
        return parseToPosition(console.nextLine());
    }

    public SetUp readSetUp() {
        System.out.println("기물 배치 전략을 선택해주세요.");
        System.out.println("1. 안상 차림");
        System.out.println("2. 바깥상 차림");
        System.out.println("3. 오른상 차림");
        System.out.println("4. 왼상 차림");
        return SetUp.getValue(console.nextLine());
    }

    private Position parseToPosition(final String input) {
        List<String> positionElements = List.of(input.split(","));
        int row = Integer.parseInt(positionElements.getFirst());
        int column = Integer.parseInt(positionElements.getLast());

        return Position.of(row, column);
    }
}
