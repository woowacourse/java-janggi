package janggi;

import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.service.JanggiService;
import janggi.view.InitializeView;
import janggi.view.JanggiBoardView;

public class Application {
    public static void main(String[] args) {
        InitializeView initializeView = new InitializeView();
        JanggiBoardView janggiBoardView = new JanggiBoardView();
        JanggiGame janggiGame = new JanggiGame(initializeView, janggiBoardView,
                new JanggiService(new GameDao(), new PieceDao()));

        janggiGame.start();
    }
}
