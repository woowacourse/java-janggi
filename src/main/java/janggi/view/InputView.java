package janggi.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private final Scanner sc;

    public InputView() {
        sc = new Scanner(System.in);
    }

    public List<Integer> readOpeningFormationChoice() {
        System.out.println("한과 초의 차림을 선택하세요.(쉼표로 구분)\n" +
                "1. 왼상차림 (상마상마)\n" +
                "2. 오른상 차림 (마상마상)\n" +
                "3. 안상 차림 (마상상마)\n" +
                "4. 바깥상 차림 (상마마상)");
        return Arrays.stream(sc.nextLine().split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }
}
