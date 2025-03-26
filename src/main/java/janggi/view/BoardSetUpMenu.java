package janggi.view;

import janggi.domain.board.BoardSetUp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum BoardSetUpMenu {
    INNER_ELEPHANT_MENU(BoardSetUp.INNER_ELEPHANT, "1", "마상마상"),
    OUTER_ELEPHANT_MENU(BoardSetUp.OUTER_ELEPHANT, "2", "상마마상"),
    RIGHT_ELEPHANT_MENU(BoardSetUp.RIGHT_ELEPHANT, "3", "마상마상"),
    LEFT_ELEPHANT_MENU(BoardSetUp.LEFT_ELEPHANT, "4", "상마상마");

    private final BoardSetUp boardSetUp;
    private final String menuOption;
    private final String menuLabel;

    BoardSetUpMenu(BoardSetUp boardSetUp, String menuOption, String menuLabel) {
        this.boardSetUp = boardSetUp;
        this.menuOption = menuOption;
        this.menuLabel = menuLabel;
    }

    public static List<String> getSetUpGuide() {
        List<String> setUpGuide = new ArrayList<>();
        for (BoardSetUpMenu boardSetUpMenu : BoardSetUpMenu.values()) {
            setUpGuide.add(boardSetUpMenu.menuOption + ". " + boardSetUpMenu.menuLabel);
        }
        return setUpGuide;
    }

    public static BoardSetUp getBoardSetUp(String menu) {
        return Arrays.stream(BoardSetUpMenu.values())
                .filter(boardSetUpMenu -> boardSetUpMenu.menuOption.equals(menu))
                .findFirst()
                .map(boardSetUpMenu -> boardSetUpMenu.boardSetUp)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력입니다."));
    }
}
