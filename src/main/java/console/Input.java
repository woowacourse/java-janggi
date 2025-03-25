package console;

import java.util.Scanner;

public class Input {
    private final Scanner scanner = new Scanner(System.in);

    public String read() {
        System.out.println("이동할 기물의 현재 위치와 이동할 대상 위치를 좌표로 입력해주세요.(ex. i1 i2)");
        return scanner.nextLine();
    }

}