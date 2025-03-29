package janggi;

import janggi.controller.JanggiController;
import janggi.dao.BoardDao;
import janggi.dao.JanggiDao;
import janggi.dao.JdbcBoardDao;
import janggi.dao.JdbcJanggiDao;
import janggi.repository.BoardRepository;
import janggi.repository.BoardRepositoryImpl;
import janggi.repository.JanggiRepository;
import janggi.repository.JanggiRepositoryImpl;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiDao janggiDao = new JdbcJanggiDao();
        BoardDao boardDao = new JdbcBoardDao();
        JanggiRepository janggiRepository = new JanggiRepositoryImpl(janggiDao, boardDao);
        BoardRepository boardRepository = new BoardRepositoryImpl(boardDao);
        JanggiService janggiService = new JanggiService(janggiRepository, boardRepository);
        JanggiController janggiController = new JanggiController(inputView, outputView, janggiService);
        janggiController.run();
    }
}
