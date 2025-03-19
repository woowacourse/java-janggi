package view;

import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public static SangMaOrderCommand inputSangMaOrder(String teamName) {
        System.out.printf("""
                %n%s나라 상마 순서를 입력해주세요.
                1. 상마상마
                2. 상마마상
                3. 마상상마
                4. 마상마상
                """, teamName);
        String input = scanner.nextLine();
        return SangMaOrderCommand.from(input);
    }
}
