package janggi;

import janggi.data.PieceDao;
import janggi.data.BoardDao;
import janggi.view.InitializeView;
import janggi.view.JanggiBoardView;

public class Application {
    public static void main(String[] args) {
        InitializeView initializeView = new InitializeView();
        JanggiBoardView janggiBoardView = new JanggiBoardView();
        PieceDao pieceDao = new PieceDao();
        BoardDao boardDao = new BoardDao(pieceDao);
        JanggiGame janggiGame = new JanggiGame(initializeView, janggiBoardView, boardDao);
        janggiGame.start();
    }
}
