package view;

import java.util.List;
import java.util.Scanner;

public class InputView {

    private Scanner sc = new Scanner(System.in);

    public String askChoPlayerName() {
        System.out.println("선공 닉네임을 입력하세요.");
        return sc.nextLine();
    }

    public String askHanPlayerName() {
        System.out.println("후공 닉네임을 입력하세요.");
        return sc.nextLine();
    }

    public int askChoPositionInput() {
        System.out.println("선공 배치 선택 (1-상마상마, 2-마상마상, 3-마상상마, 4-상마마상)");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력해주세요.");
        }
    }

    public int askHanPositionInput() {
        System.out.println("후공 배치 선택 (1-상마상마, 2-마상마상, 3-마상상마, 4-상마마상)");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력해주세요.");
        }
    }

    public List<Integer> askSourcePosition() {
        System.out.println("기물 위치를 입력하세요.");
        return askPosition();
    }

    public List<Integer> askDestinationPosition() {
        System.out.println("이동 위치를 입력하세요.");
        return askPosition();
    }

    private List<Integer> askPosition() {
        try {
            List<String> splitString = List.of(sc.nextLine().split(" "));
            return splitString.stream()
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자 두 개를 공백으로 구분하여 입력하세요.");
        }
    }
}
