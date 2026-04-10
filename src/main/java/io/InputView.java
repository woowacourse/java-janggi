package io;

import domain.TeamColor;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView() {
        this(new Scanner(System.in));
    }

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readFormationChoice(TeamColor teamColor) {
        System.out.print(teamColor.getDisplayName() + " 상차림 선택 (1. 안상 2. 바깥상 3. 좌상 4. 우상) > ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int readPieceChoice(TeamColor teamColor) {
        System.out.print(teamColor.getDisplayName() + " 차례, 선택할 기물 번호 > ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int readRouteChoice() {
        System.out.print("이동할 경로 번호 선택 (0은 뒤로가기) > ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int readResumeOrNewChoice() {
        System.out.print("선택 (1 또는 2) > ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int readTimeLimitSeconds() {
        System.out.print("제한 시간(초) > ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

}
