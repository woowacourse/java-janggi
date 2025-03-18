package janggi.view;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readSetupOption() {
        System.out.println("=== 기물표기법 ===\n"
                + "  한 초\n"
                + "궁 k K\n"
                + "차 c C\n"
                + "포 p P\n"
                + "마 h H\n"
                + "상 e E\n"
                + "사 g G\n"
                + "병 s S\n");
        System.out.println("상차림을 입력하세요.\n"
                + "1. 안상\n"
                + "2. 바깥상\n"
                + "3. 오른상\n"
                + "4. 왼상");
        return scanner.nextLine();
    }
}
