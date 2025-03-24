package janggi;

import janggi.manager.JanggiGame;
import janggi.view.Viewer;

public class Application {

    public static void main(String[] args) {
        Viewer viewer = new Viewer();
        JanggiGame janggiGame = new JanggiGame(viewer);

        janggiGame.start();
    }
}
