package io;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readSetupCommand() {
        return scanner.nextLine();
    }


}
