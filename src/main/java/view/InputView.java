package view;

import domain.game.SetUp;
import domain.position.Position;
import domain.player.Player;
import domain.player.Team;
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

    public SetUp readSetUp(final Team team) {
        System.out.printf("팀 %s의 기물 차림을 선택해 주세요.\n", team.getName());
        System.out.println("1. 안상 차림");
        System.out.println("2. 바깥상 차림");
        System.out.println("3. 오른상 차림");
        System.out.println("4. 왼상 차림");
        return SetUp.getValue(console.nextLine());
    }

    public String getName(final Team team) {
        System.out.printf("팀 %s 플레이어의 이름을 입력해 주세요.\n", team.getName());
        return console.nextLine();
    }

    private Position parseToPosition(final String input) {
        List<String> positionElements = List.of(input.split(","));
        int row = Integer.parseInt(positionElements.getFirst());
        int column = Integer.parseInt(positionElements.getLast());

        return Position.of(row, column);
    }
}
