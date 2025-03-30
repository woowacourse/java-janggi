package janggi;

import janggi.dao.JdbcGameDao;
import janggi.dao.JdbcPieceDao;
import janggi.service.JanggiService;
import janggi.view.InitializeView;
import janggi.view.JanggiBoardView;

public class Application {
    public static void main(String[] args) {
        InitializeView initializeView = new InitializeView();
        JanggiBoardView janggiBoardView = new JanggiBoardView();
        JanggiService janggiService = new JanggiService(new JdbcGameDao(), new JdbcPieceDao());
        JanggiGame janggiGame = new JanggiGame(initializeView, janggiBoardView, janggiService);
        janggiGame.start();
    }
}
