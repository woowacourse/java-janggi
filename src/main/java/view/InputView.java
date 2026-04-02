package view;

import domain.board.ElephantSetup;
import java.util.Scanner;

public class InputView {

    private static final int USER_INPUT_START_INDEX = 1;

    private static final Scanner SCANNER = new Scanner(System.in);

    public String readPlayerName() {
        return SCANNER.nextLine();
    }

    public ElephantSetup readElephantSetup() {
        String setup = SCANNER.nextLine();
        return ElephantSetup.of(Integer.parseInt(setup));
    }

    public int readPieceIndex() {
        return Integer.parseInt(SCANNER.nextLine().trim());
    }

    public int readPositionIndex() {
        int positionIndex = Integer.parseInt(SCANNER.nextLine().trim());
        return toZeroBasedIndex(positionIndex);
    }

    private int toZeroBasedIndex(int userInputNumber) {
        return userInputNumber - USER_INPUT_START_INDEX;
    }
}
