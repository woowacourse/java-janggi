package janggi.view;

import janggi.domain.Dynasty;
import janggi.domain.player.Player;
import janggi.domain.board.BoardSetUp;
import java.util.Scanner;

public class InitializeView {

    private static final Scanner scanner = new Scanner(System.in);

    public String readPlayerNickname(Dynasty dynasty) {
        if (dynasty == Dynasty.HAN) {
            System.out.println("\n한나라를 플레이 할 닉네임을 입력해주세요.");
            return readLine();
        }
        System.out.println("\n초나라를 플레이 할 닉네임을 입력해주세요.");
        return readLine();
    }

    public BoardSetUp readBoardSetUp(Player player) {
        System.out.println("\n" + player.getNickname() + "의 상차림을 선택해 주세요.");
        printBoardSetUpGuide();
        String menu = readLine();
        return switch (menu) {
            case "1" -> BoardSetUp.RIGHT_ELEPHANT;
            case "2" -> BoardSetUp.LEFT_ELEPHANT;
            case "3" -> BoardSetUp.OUTER_ELEPHANT;
            case "4" -> BoardSetUp.INNER_ELEPHANT;
            default -> throw new IllegalArgumentException("잘못된 입력입니다.");
        };
    }

    private void printBoardSetUpGuide() {
        System.out.print("""
                1. 마상마상
                2. 상마상마
                3. 상마마상
                4. 마상상마
                """);
    }

    private String readLine() {
        return scanner.nextLine().trim();
    }
}
