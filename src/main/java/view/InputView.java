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
        // TODO: 상차림 매핑 추가
        return ElephantSetup.InnerElephantSetup;
    }
}
