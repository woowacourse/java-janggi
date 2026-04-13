package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputView {
    private static final String DELIMITER = ",";
    private final Scanner sc = new Scanner(System.in);

    public List<Integer> inputMovePiece() {
        System.out.println("어떤 기물을 옮기시겠습니까?(예시 : 0,0)");
        return parseToCoordinate(sc.nextLine());
    }

    public List<Integer> inputTargetPosition() {
        System.out.println("어디로 옮기시겠습니까?(예시 : 1,0)");
        return parseToCoordinate(sc.nextLine());
    }

    private List<Integer> parseToCoordinate(String input) {
        return Arrays.stream(input.split(DELIMITER))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
