package janggi;

import janggi.controller.GameLobbyController;
import janggi.domain.game.GameManager;
import janggi.persistence.DatabaseInitializer;
import janggi.persistence.DatabaseProvider;
import janggi.persistence.dao.JdbcBoardDao;
import janggi.persistence.dao.JdbcGameDao;
import janggi.persistence.repository.JdbcGameRepository;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.sql.Connection;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiService janggiService = new JanggiService(new JdbcGameRepository(new JdbcGameDao(), new JdbcBoardDao()));
        runGameLifecycle(inputView, outputView, janggiService);
        inputView.close();
    }

    private static void runGameLifecycle(InputView inputView, OutputView outputView, JanggiService janggiService) {
        try (Connection connection = DatabaseProvider.getConnection()) {
            DatabaseInitializer.initialize(connection);
            GameLobbyController lobbyController = new GameLobbyController(inputView, outputView, janggiService);
            GameManager gameManager = lobbyController.enterLobby(connection);
            Runner runner = new Runner(inputView, outputView, janggiService);
            runner.run(connection, gameManager);
        } catch (SQLException error) {
            outputView.printLine("[ERROR] 게임 진행 중 DB 정보 조회에 실패했습니다." + "\n" + error.getMessage());
        }
    }
}
