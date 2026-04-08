package janggi.view;

import janggi.dto.SideDto;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readGameCommand() {
        System.out.println("장기 게임을 시작합니다.");
        System.out.println("1: 새로운 게임 시작");
        System.out.println("2: 기존 게임 이어하기");
        return scanner.nextLine();
    }

    public String readGameId() {
        System.out.println("불러올 게임 ID를 입력하세요.");
        return scanner.nextLine();
    }

    public String readPlayerName(SideDto sideDto) {
        System.out.printf("%s나라 플레이어 이름 입력: ", sideDto.name());
        return scanner.nextLine();
    }

    public String readFormation(SideDto sideDto) {
        String message = String.format("""
            %s나라 플레이어 포메이션 입력
            1. 상마상마
            2. 마상마상
            3. 상마마상
            4. 마상상마""", sideDto.name());

        System.out.println(message);
        return scanner.nextLine();
    }

    public String readSourcePosition(SideDto sideDto) {
        System.out.println(sideDto.name() + "나라 차례입니다. 이동 시킬 기물의 위치를 입력하세요. ex) 0,3");
        return scanner.nextLine();
    }

    public String readTargetPosition() {
        System.out.println("이동할 위치를 입력하세요. ex) 0,3");
        return scanner.nextLine();
    }
}
