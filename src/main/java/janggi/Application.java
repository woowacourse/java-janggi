package janggi;

import janggi.data.PieceDao;
import janggi.data.PointDao;
import janggi.view.InitializeView;
import janggi.view.JanggiBoardView;

public class Application {
    public static void main(String[] args) {
        InitializeView initializeView = new InitializeView();
        JanggiBoardView janggiBoardView = new JanggiBoardView();
        PieceDao pieceDao = new PieceDao();
        PointDao pointDao = new PointDao(pieceDao);
        JanggiGame janggiGame = new JanggiGame(initializeView, janggiBoardView, pointDao);
        janggiGame.start();
    }
}
