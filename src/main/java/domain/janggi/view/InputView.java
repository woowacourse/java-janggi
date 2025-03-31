package domain.janggi.view;

import domain.janggi.domain.Position;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public String inputMovePositions() {
        System.out.println(String.format("""
                이동하고자 하는 기물의 위치와 목표 위치를 입력해주세요.
                게임을 종료하려면 Q를 입력해주세요.
                입력 예시: 11, 23
                """));
        return scanner.nextLine();
    }
}
