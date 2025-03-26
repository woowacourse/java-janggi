package janggi.view;

import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public String inputMovePositions(String color) {
        System.out.println(String.format("""
                이동하고자 하는 기물의 위치와 목표 위치를 입력해주세요.
                게임을 종료하려면 Q를 입력해주세요.
                현재 턴: %s
                입력 예시: 11, 23
                """, color));
        return scanner.nextLine();
    }
}
