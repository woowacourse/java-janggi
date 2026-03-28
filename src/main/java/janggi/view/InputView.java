package janggi.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private final Scanner sc;

    public InputView() {
        this.sc = new Scanner(System.in);
    }

    public List<Integer> playTurn(String team) {
        System.out.println(team + "나라 턴입니다.");

        List<Integer> inputFrom = readCoordinates("이동할 기물의 좌표를 입력하세요. (예 : 1, 2)");
        List<Integer> inputTo = readCoordinates("도착할 좌표를 입력하세요. (예 : 1, 3)");

        List<Integer> result = new ArrayList<>(inputFrom);
        result.addAll(inputTo);

        return result;
    }

    private List<Integer> readCoordinates(String message) {
        System.out.println(message);
        return Arrays.stream(sc.nextLine().split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }
}
