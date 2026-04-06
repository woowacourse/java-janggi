package janggi;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;

public class Application {
    public static void main(String[] args) {
        Board board = new Board(BoardFactory.settingUpBoard());
        JanggiGame janggiGame = new JanggiGame(board);
        janggiGame.run();
    }
}
