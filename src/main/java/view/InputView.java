package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public int getStartingPosition() {
        System.out.println("상차림을 입력하세요.\n"
                + "1 : 마상마상\n"
                + "2 : 상마상마\n"
                + "3 : 상마마상\n"
                + "4 : 마상상마");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            return getStartingPosition();
        }
    }

    public List<String> readMoveCommand() {
        System.out.println("이동할 말을 입력하세요 (예: move 가1 자8):");
        final String input = scanner.nextLine();
        final String pattern = "move [가-자][0-9] [가-자][0-9]";

        if (!input.matches(pattern)) {
            throw new IllegalArgumentException("[ERROR] 올바른 형식으로 입력해주세요. (예: move 가1 자8)");
        }

        List<String> moveInfo = new ArrayList<>();
        moveInfo.add(String.valueOf(input.charAt(5)));
        moveInfo.add(String.valueOf(input.charAt(6)));
        moveInfo.add(String.valueOf(input.charAt(8)));
        moveInfo.add(String.valueOf(input.charAt(9)));

        return moveInfo;
    }
}