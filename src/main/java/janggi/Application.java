package janggi;

import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        Board board = new Board();
        OutputView outputView = new OutputView();
        outputView.printBoard(board.getBoard());
    }
}
