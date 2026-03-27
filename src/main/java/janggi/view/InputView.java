package janggi.view;

import janggi.utils.Parser;
import janggi.view.reader.Console;

public final class InputView {

    private InputView() {
    }

    public static int readSetupCommand() {
        return Parser.parseInteger(Console.readLine());
    }
}
