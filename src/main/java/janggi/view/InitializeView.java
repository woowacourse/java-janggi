package janggi.view;

import janggi.domain.board.Dynasty;
import janggi.domain.board.BoardSetUp;
import janggi.domain.player.Player;
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
        for (String boardSetUpLabel : BoardSetUpMenu.getSetUpGuide()) {
            System.out.println(boardSetUpLabel);
        }
        String menu = readLine();
        return BoardSetUpMenu.getBoardSetUp(menu);
    }

    private String readLine() {
        return scanner.nextLine().trim();
    }
}
