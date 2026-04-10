package io;

import domain.piece.TeamColor;
import java.util.Scanner;

public class InputView {
    private static final String GAME_START_CHOICE_PROMPT = "시작 옵션 선택 (1. 이어하기 2. 새 게임) > ";
    private static final String FORMATION_CHOICE_PROMPT = " 상차림 선택 (1. 안상 2. 바깥상 3. 좌상 4. 우상) > ";
    private static final String PIECE_CHOICE_PROMPT = " 차례, 선택할 기물 번호 > ";
    private static final String ROUTE_CHOICE_PROMPT = "이동할 경로 번호 선택 (0은 뒤로가기) > ";

    private final Scanner scanner;

    public InputView() {
        this(new Scanner(System.in));
    }

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readGameStartChoice() {
        System.out.print(GAME_START_CHOICE_PROMPT);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int readFormationChoice(TeamColor teamColor) {
        System.out.print(teamColor.displayName() + FORMATION_CHOICE_PROMPT);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int readPieceChoice(TeamColor teamColor) {
        System.out.print(teamColor.displayName() + PIECE_CHOICE_PROMPT);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int readRouteChoice() {
        System.out.print(ROUTE_CHOICE_PROMPT);
        return Integer.parseInt(scanner.nextLine().trim());
    }

}

