package view;

import domain.board.BoardPoint;
import dto.MovementRequestDto;
import execptions.JanggiArgumentException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class InputView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int START_POINT_INDEX = 0;
    private static final int ARRIVAL_POINT_INDEX = 1;
    private static final int VALID_POINT_SIZE = 2;
    public static final String SPLITTER = ",";

    public MovementRequestDto readMovementRequest() {
        System.out.println("출발점과 도착점의 위치를 알려주세요 ex.2,1 3,1");
        final String input = scanner.nextLine();

        validateInput(input);
        final String[] splitInput = input.split(" ");

        final List<Integer> rawStartPoint = formatToIntegerList(splitInput, START_POINT_INDEX);
        final BoardPoint startBoardPoint = new BoardPoint(rawStartPoint.getFirst(), rawStartPoint.getLast());

        final List<Integer> rawArrivalPoint = formatToIntegerList(splitInput, ARRIVAL_POINT_INDEX);
        final BoardPoint arrivalBoardPoint = new BoardPoint(rawArrivalPoint.getFirst(), rawArrivalPoint.getLast());

        return new MovementRequestDto(startBoardPoint, arrivalBoardPoint);
    }

    private void validateInput(final String input) {
        final String[] splitInput = input.split(" ");

        validateSize(splitInput);
        validateFormat(splitInput);
    }

    private static void validateFormat(String[] input) {
        if (!input[START_POINT_INDEX].contains(SPLITTER) || !input[ARRIVAL_POINT_INDEX].contains(SPLITTER)) {
            throw new JanggiArgumentException("2,1 3,1 와 같이 쉼표(,) 를 통해 구분되어야 합니다.");
        }
    }

    private static void validateSize(String[] input) {
        if (input.length != VALID_POINT_SIZE) {
            throw new JanggiArgumentException("2,1 3,1 와 같이 출발점과 도착점을 모두 명시해야 합니다.");
        }
    }

    private static List<Integer> formatToIntegerList(final String[] splitInput, final int index) {
        return Arrays.stream(splitInput[index].split(","))
                .map(Integer::parseInt)
                .toList();
    }
}
