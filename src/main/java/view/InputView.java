package view;

import java.util.Scanner;

import dto.CommandDto;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public CommandDto command() {
        System.out.println("""
            이동할 기물의 현재 위치와 이동할 대상 위치를 좌표로 입력해주세요.(ex. i1 i2)
            (Q를 입력하여 기권할 수 있습니다.)""");

        return CommandDto.from(scanner.nextLine());
    }

}
