package janggi;

import janggi.domain.Board;
import janggi.factory.PieceFactory;
import janggi.view.Viewer;

public class Application {

    public static void main(String[] args) {
        Board board = new Board(PieceFactory.initialize());

        Viewer viewer = new Viewer();
        viewer.printBoard(board);
    }
}
