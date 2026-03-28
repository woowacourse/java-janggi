package view;

import domain.board.ElephantSetup;
import java.util.Scanner;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    public String readPlayerName() {
        return SCANNER.nextLine();
    }

    public ElephantSetup readElephantSetup() {
        String setup = SCANNER.nextLine();
        return ElephantSetup.of(Integer.parseInt(setup));
    }

    public int readPieceNumber() {
        return Integer.parseInt(SCANNER.nextLine().trim());
    }

    public int readPositionNumber() {
        return Integer.parseInt(SCANNER.nextLine().trim());
    }
}
