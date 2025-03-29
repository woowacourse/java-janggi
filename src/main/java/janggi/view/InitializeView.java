package janggi.view;

import janggi.domain.board.BoardSetUp;
import janggi.domain.board.ChuBoardSetUp;
import janggi.domain.board.HanBoardSetUp;
import janggi.domain.piece.Dynasty;
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

    public BoardSetUp readBoardSetUp(Dynasty dynasty) {
        System.out.println("\n" + toDynastyName(dynasty) + "의 상차림을 선택해 주세요.");
        printBoardSetUpGuide();
        String menu = readLine();
        if (dynasty == Dynasty.HAN) {
            if (menu.equals("1")) {
                return HanBoardSetUp.RIGHT_ELEPHANT;
            }
            if (menu.equals("2")) {
                return HanBoardSetUp.LEFT_ELEPHANT;
            }
            if (menu.equals("3")) {
                return HanBoardSetUp.OUTER_ELEPHANT;
            }
            return HanBoardSetUp.INNER_ELEPHANT;
        }
        if (menu.equals("1")) {
            return ChuBoardSetUp.RIGHT_ELEPHANT;
        }
        if (menu.equals("2")) {
            return ChuBoardSetUp.LEFT_ELEPHANT;
        }
        if (menu.equals("3")) {
            return ChuBoardSetUp.OUTER_ELEPHANT;
        }
        return ChuBoardSetUp.INNER_ELEPHANT;
    }

    private String toDynastyName(Dynasty dynasty) {
        if (dynasty == Dynasty.HAN) {
            return "한나라";
        }
        return "초나라";
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
