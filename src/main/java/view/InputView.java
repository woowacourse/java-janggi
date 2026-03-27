package view;

import domain.board.ElephantSetup;
import java.util.Scanner;

public class InputView {

    private static final String EMPTY_INPUT = "빈 칸을 입력할 수 없습니다.";

    private static final Scanner SCANNER = new Scanner(System.in);

    public String readPlayerName() {
        String input = SCANNER.nextLine();
        validateEmpty(input);
        return input.trim();
    }

    public ElephantSetup readElephantSetup() {
        String setup = SCANNER.nextLine();
        return ElephantSetup.of(Integer.parseInt(setup));
    }


    private void validateEmpty(final String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(EMPTY_INPUT);
        }
    }
}
