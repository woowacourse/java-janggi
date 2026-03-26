package view;

import domain.piece.Team;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public Integer initialFormation(Team team) {
        System.out.println(team + " 진영 배치 전략을 입력 하세요.\n1. 왼상\n2. 오른상\n3. 원앙마\n4. 양귀마 ");
        try {
            int parseNumber = Integer.parseInt(scanner.nextLine());
            return validRange(parseNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 입력해주세요.");
        }

    }

    private int validRange(int number) {
        if (number < 1 || number > 4) {
            throw new IllegalArgumentException("1 ~ 4 사이의 숫자로 입력해주세요.");
        }
        return number;

    }
}
