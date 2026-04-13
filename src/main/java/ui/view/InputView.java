package ui.view;

import common.JanggiException;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String INVALID_NUMBER_INPUT = "숫자만 입력해주세요.";
    private static final String INVALID_POSITION_INPUT = "숫자 두 개를 공백으로 구분하여 입력하세요.";
    private final Scanner scanner = new Scanner(System.in);

    public String askNewOrLoadOption() {
        System.out.println("게임을 불러오시겠습니까? (y or n)");
        return scanner.nextLine();
    }

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
            throw new JanggiException(INVALID_NUMBER_INPUT);
        }
    }

    public int askHanPositionInput() {
        System.out.println("후공 배치 선택 (1-상마상마, 2-마상마상, 3-마상상마, 4-상마마상)");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new JanggiException(INVALID_NUMBER_INPUT);
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
                throw new JanggiException(INVALID_POSITION_INPUT);
            }
            return numbers;
        } catch (NumberFormatException e) {
            throw new JanggiException(INVALID_POSITION_INPUT);
        }
    }

    public long askGameId() {
        System.out.println("게임 ID를 입력하세요.");
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new JanggiException(INVALID_NUMBER_INPUT);
        }
    }
}
