package janggi.view;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readSetupOption() {
        System.out.println("상차림을 입력하세요.\n"
                + "1. 안상\n"
                + "2. 바깥상\n"
                + "3. 오른상\n"
                + "4. 왼상");
        return scanner.nextLine();
    }
}
