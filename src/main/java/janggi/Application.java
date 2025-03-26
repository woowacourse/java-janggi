package janggi;

import janggi.domain.Board;
import janggi.domain.Round;
import janggi.factory.PieceFactory;
import janggi.manager.JanggiGame;
import janggi.view.Viewer;

public class Application {

    public static void main(String[] args) {
        Viewer viewer = new Viewer();
        Board board = new Board(PieceFactory.initialize());
        Round round = new Round(board);
        JanggiGame janggiGame = new JanggiGame(viewer, round);

        janggiGame.start();
    }
}
