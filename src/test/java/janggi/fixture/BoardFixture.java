package janggi.fixture;

import janggi.board.Board;
import janggi.board.BoardInitializer;

public class BoardFixture {

    public static Board createBasicBoard() {
        BoardInitializer boardInitializer = new BoardInitializer();
        return boardInitializer.makeBoard();
    }
}
