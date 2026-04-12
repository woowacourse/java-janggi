package janggi.config;

import janggi.controller.JanggiController;
import janggi.persistence.GameRepositoryImpl;
import janggi.persistence.dao.GameDao;
import janggi.persistence.dao.JdbcGameDao;
import janggi.persistence.dao.JdbcPieceDao;
import janggi.persistence.dao.PieceDao;
import janggi.persistence.mapper.GameMapper;
import janggi.persistence.mapper.PieceMapper;
import janggi.service.GameRepository;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class AppConfig {
    private final ConnectionPool connectionPool = DatabaseConfig.getPool();

    public JanggiController janggiController() {
        return new JanggiController(inputView(), outputView(), janggiService());
    }

    public InputView inputView() {
        return new InputView();
    }

    public OutputView outputView() {
        return new OutputView();
    }

    public JanggiService janggiService() {
        return new JanggiService(gameRepository(), transactionManager());
    }

    public TransactionManager transactionManager() {
        return new TransactionManager(connectionPool);
    }

    public GameRepository gameRepository() {
        return new GameRepositoryImpl(gameDao(), pieceDao(), gameMapper(), pieceMapper());
    }

    public GameDao gameDao() {
        return new JdbcGameDao(connectionPool);
    }

    public PieceDao pieceDao() {
        return new JdbcPieceDao(connectionPool);
    }

    public GameMapper gameMapper() {
        return new GameMapper();
    }

    public PieceMapper pieceMapper() {
        return new PieceMapper();
    }
}
