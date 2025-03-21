package janggi.view;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readStartPosition() {
        System.out.println("움직이고 싶은 말의 위치를 입력하세요. (x,y) (종료하고 싶으시다면, Q를 입력해주세요.)");
        return scanner.nextLine();
    }

    public String readEndPosition() {
        System.out.println("움직일 위치를 입력하세요. (x,y)");
        return scanner.nextLine();
    }
}
