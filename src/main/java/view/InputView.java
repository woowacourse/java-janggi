package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public List<List<Integer>> readMovementRequest() {
        System.out.println("출발점과 도착점의 위치를 알려주세요 ex.2,1 3,1");
        String input = scanner.nextLine();
        String[] splitInput = input.split(" ");
        List<Integer> originPoint = formatToIntegerList(splitInput, 0);
        List<Integer> arrivalPoint = formatToIntegerList(splitInput, 1);
        return List.of(originPoint, arrivalPoint);
    }

    private static List<Integer> formatToIntegerList(String[] splitInput, int index) {
        return Arrays.stream(splitInput[index].split(","))
                .map(Integer::parseInt)
                .toList();
    }
}
