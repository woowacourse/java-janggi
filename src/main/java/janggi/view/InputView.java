package janggi.view;

import java.util.Scanner;

public class InputView {
    Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getBlueHorsePosition() {
        return scanner.nextLine();
    }

    public String getRedHorsePosition() {
        return scanner.nextLine();
    }
}
