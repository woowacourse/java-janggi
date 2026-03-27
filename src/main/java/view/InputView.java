package view;

import static common.exception.ErrorMessage.INVALID_NUMBER_INPUT;
import static common.exception.ErrorMessage.INVALID_POSITION_INPUT;

import common.exception.JanggiException;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String askChoPlayerName() {
        System.out.println("선공 닉네임을 입력하세요.");
        return scanner.nextLine();
    }

    public String askHanPlayerName() {
        System.out.println("후공 닉네임을 입력하세요.");
        return scanner.nextLine();
    }

    public int askChoPositionInput() {
        System.out.println("선공 배치 선택 (1-상마상마, 2-마상마상, 3-마상상마, 4-상마마상)");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new JanggiException(INVALID_NUMBER_INPUT.getMessage());
        }
    }

    public int askHanPositionInput() {
        System.out.println("후공 배치 선택 (1-상마상마, 2-마상마상, 3-마상상마, 4-상마마상)");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new JanggiException(INVALID_NUMBER_INPUT.getMessage());
        }
    }

    public List<Integer> askSourcePosition() {
        System.out.println("기물 위치를 입력하세요. (형식 : 행 열)");
        return askPosition();
    }

    public List<Integer> askDestinationPosition() {
        System.out.println("이동 위치를 입력하세요. (형식 : 행 열)");
        return askPosition();
    }

    private List<Integer> askPosition() {
        try {
            List<String> splitString = List.of(scanner.nextLine().split(" "));
            List<Integer> numbers = splitString.stream()
                    .map(Integer::parseInt)
                    .toList();
            if (numbers.size() != 2) {
                throw new JanggiException(INVALID_POSITION_INPUT.getMessage());
            }
            return numbers;
        } catch (NumberFormatException e) {
            throw new JanggiException(INVALID_POSITION_INPUT.getMessage());
        }
    }
}
