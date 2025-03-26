package janggi.view;

import janggi.domain.piece.Team;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(final Scanner scanner) {
        this.scanner = scanner;
    }

    public String getBlueHorsePosition() {
        System.out.println("청팀의 상, 마 순서를 입력해주세요(ex: 상마상마)");
        return scanner.nextLine();
    }

    public String getRedHorsePosition() {
        System.out.println("홍팀의 상, 마 순서를 입력해주세요(ex: 상마상마)");
        return scanner.nextLine();
    }

    public String getPieceMovement(final Team team) {
        System.out.println(team.getName() + ": 이동 할 기물의 위치와 이동 시킬 위치를 입력해주세요(ex: 109 89");
        return scanner.nextLine();
    }
}