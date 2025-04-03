package janggi;

import janggi.data.BoardDao;
import janggi.view.InitializeView;
import janggi.view.JanggiBoardView;

public class Application {
    public static void main(String[] args) {
        InitializeView initializeView = new InitializeView();
        JanggiBoardView janggiBoardView = new JanggiBoardView();
        BoardDao boardDao = new BoardDao();
        JanggiGame janggiGame = new JanggiGame(initializeView, janggiBoardView, boardDao);
        janggiGame.start();
    }
}
