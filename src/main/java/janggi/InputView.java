package janggi;

import java.util.Scanner;

public class InputView {

    private final static Scanner scanner = new Scanner(System.in);

    public Answer readAnswer(final CampType campType) {
        System.out.printf("%s의 초기 배치를 선택해주세요.", campType.getName());
        System.out.println("1. 왼상(상마상마)");
        System.out.println("2. 오른상(마상마상)");
        System.out.println("3. 안상(마상상마)");
        System.out.println("4. 바깥상(상마마상)");

        return Answer.from(scanner.nextLine());
    }

}
