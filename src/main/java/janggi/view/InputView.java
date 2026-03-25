package janggi.view;

import janggi.domain.dto.MoveCommand;
import java.util.Arrays;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public MoveCommand readMovePositions() {

        System.out.println("장기를 이동시킬 좌표들을 이동할행 이동할열 도착할행 도착할열 형태로 입력해 주세요.");
        System.out.println("ex 1 1 2 2");

        String rawInput = scanner.nextLine();
        String[] input = rawInput.split(" "); // TODO 검증
        int[] numbers = Arrays.stream(rawInput.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        return MoveCommand.from(numbers);
    }


}
