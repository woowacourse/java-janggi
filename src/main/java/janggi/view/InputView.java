package janggi.view;

import janggi.domain.dto.MoveCommand;
import janggi.domain.piece.Team;

import java.util.Arrays;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public MoveCommand readMovePositions(Team team) {
        String teamName = "";
        if (team == Team.CHO) teamName = "초나라";
        if (team == Team.HAN) teamName = "한나라";
        
        System.out.println(teamName + "의 턴입니다.");
        System.out.println("장기를 이동시킬 좌표들을 이동할행 이동할열 도착할행 도착할열 형태로 입력해 주세요.");
        System.out.println("ex 1 1 2 2");

        String rawInput = scanner.nextLine();
        int[] numbers = Arrays.stream(rawInput.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        return MoveCommand.from(numbers);
    }


}
