package janggi;

import janggi.controller.JanggiController;
import janggi.dao.BoardDao;
import janggi.dao.JanggiDao;
import janggi.dao.JdbcBoardDao;
import janggi.dao.JdbcJanggiDao;
import janggi.dao.JdbcPieceDao;
import janggi.dao.PieceDao;
import janggi.repository.BoardRepository;
import janggi.repository.JanggiRepository;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiDao janggiDao = new JdbcJanggiDao();
        BoardDao boardDao = new JdbcBoardDao();
        PieceDao pieceDao = new JdbcPieceDao();
        JanggiRepository janggiRepository = new JanggiRepository(janggiDao, boardDao, pieceDao);
        BoardRepository boardRepository = new BoardRepository(boardDao, pieceDao);
        JanggiService janggiService = new JanggiService(janggiRepository, boardRepository);
        JanggiController janggiController = new JanggiController(inputView, outputView, janggiService);
        janggiController.run();
    }
}
