package janggi;

import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        Board board = Board.init();
        OutputView outputView = new OutputView();
        outputView.printBoard(board.getBoard());
    }
}
