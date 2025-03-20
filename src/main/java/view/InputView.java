package view;

import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public String inputHanHorseElephantLocation() {
        System.out.println("""
                한나라는 아래 중 하나를 선택해주세요.
                1. 馬象馬象(마상마상)
                2. 象馬象馬(상마상마)
                3. 馬象象馬(마상상마)
                4. 象馬馬象(상마마상)
                """);
        return scanner.nextLine();
    }

    public int requestStartX() {
        System.out.println("움직일 기물의 X좌표를 입력해주세요");
        return Integer.parseInt(scanner.nextLine());
    }

    public int requestStartY() {
        System.out.println("움직일 기물의 Y좌표를 입력해주세요");
        return Integer.parseInt(scanner.nextLine());
    }

    public int requestDestinationX() {
        System.out.println("목표 위치의 X좌표를 입력해주세요");
        return Integer.parseInt(scanner.nextLine());
    }

    public int requestDestinationY() {
        System.out.println("목표 위치의 Y좌표를 입력해주세요");
        return Integer.parseInt(scanner.nextLine());
    }
}
