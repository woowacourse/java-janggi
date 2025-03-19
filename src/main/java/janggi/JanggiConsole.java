package janggi;

import janggi.board.Board;
import janggi.board.BoardFactory;
import janggi.view.OutputView;

public class JanggiConsole {
    public void start() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.makeBoard();
        OutputView outputView = new OutputView();
        outputView.printBoard(board.getPieces());
    }
}
