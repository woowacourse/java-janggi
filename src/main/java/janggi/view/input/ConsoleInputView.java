package janggi.view.input;

import java.util.Scanner;

public class ConsoleInputView implements InputView {

    private final Scanner scanner;

    public ConsoleInputView() {
        scanner = new Scanner(System.in);
    }

    public ConsoleInputView(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
