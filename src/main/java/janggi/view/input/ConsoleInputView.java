package janggi.view.input;

import java.util.Optional;
import java.util.Scanner;

public class ConsoleInputView implements InputView {

    private static final String CANCEL = "cancel";

    private final Scanner scanner;

    public ConsoleInputView() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public Optional<String> readCancelableLine() {
        String command = readLine();
        if (CANCEL.equalsIgnoreCase(command.trim())) {
            return Optional.empty();
        }
        return Optional.of(command);
    }
}
