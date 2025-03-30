package janggi;

import janggi.db.BoardStatus;
import janggi.game.Manager;

public class Application {
    public static void main(String[] args) {
        BoardStatus boardStatus = new BoardStatus();
        boardStatus.createBoardStatus();

        Manager manager = new Manager(boardStatus);
        manager.run();
    }
}
