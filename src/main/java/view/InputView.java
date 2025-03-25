package view;

import domain.board.Point;
import execptions.JanggiArgumentException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class InputView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int START_POINT_INDEX = 0;
    private static final int ARRIVAL_POINT_INDEX = 1;

    public List<Point> readMovementRequest() {
        System.out.println("출발점과 도착점의 위치를 알려주세요 ex.2,1 3,1");
        final String input = scanner.nextLine();
        final String[] splitInput = input.split(" ");
        validateInput(splitInput);

        final List<Integer> rawStartPoint = formatToIntegerList(splitInput, START_POINT_INDEX);
        final Point startPoint = new Point(rawStartPoint.getFirst(), rawStartPoint.getLast());

        final List<Integer> rawArrivalPoint = formatToIntegerList(splitInput, ARRIVAL_POINT_INDEX);
        final Point arrivalPoint = new Point(rawArrivalPoint.getFirst(), rawArrivalPoint.getLast());

        return List.of(startPoint, arrivalPoint);
    }

    private void validateInput(final String[] input) {
        validateSize(input);
        validateFormat(input);
    }

    private static void validateFormat(String[] input) {
        if (!input[0].contains(",") || !input[1].contains(",")) {
            throw new JanggiArgumentException("2,1 3,1 와 같이 쉼표(,) 를 통해 구분되어야 합니다.");
        }
    }

    private static void validateSize(String[] input) {
        if (input.length != 2) {
            throw new JanggiArgumentException("2,1 3,1 와 같이 출발점과 도착점을 모두 명시해야 합니다.");
        }
    }

    private static List<Integer> formatToIntegerList(final String[] splitInput, final int index) {
        return Arrays.stream(splitInput[index].split(","))
                .map(Integer::parseInt)
                .toList();
    }
}
