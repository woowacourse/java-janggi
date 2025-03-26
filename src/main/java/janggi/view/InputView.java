package janggi.view;

import janggi.domain.piece.Team;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(final Scanner scanner) {
        this.scanner = scanner;
    }

    public String readHorsePosition(final Team team) {
        System.out.println(team.getName() + "팀의 상, 마 배치를 입력해주세요(ex: 상마상마)");
        return scanner.nextLine();
    }

    public String readPieceMovement(final Team team) {
        System.out.println(team.getName() + ": 이동 할 기물의 위치와 이동 시킬 위치를 입력해주세요(ex: 109 89)");
        return scanner.nextLine();
    }
}