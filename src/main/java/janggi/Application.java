package janggi;

import janggi.controller.GameLobbyController;
import janggi.persistence.ActiveGameSession;
import janggi.persistence.DatabaseInitializer;
import janggi.persistence.DatabaseProvider;
import janggi.persistence.JanggiBoardRepository;
import janggi.persistence.JanggiGameRepository;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.sql.Connection;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiService janggiService = new JanggiService(new JanggiGameRepository(), new JanggiBoardRepository());
        runGameLifecycle(inputView, outputView, janggiService);
        inputView.close();
    }

    private static void runGameLifecycle(InputView inputView, OutputView outputView, JanggiService janggiService) {
        try (Connection connection = DatabaseProvider.getConnection()) {
            DatabaseInitializer.initialize(connection);
            GameLobbyController lobbyController = new GameLobbyController(inputView, outputView, janggiService);
            ActiveGameSession session = lobbyController.enterLobby(connection);
            Runner runner = new Runner(inputView, outputView, janggiService);
            runner.run(connection, session.gameId());
        } catch (SQLException error) {
            outputView.printLine("[ERROR] 게임 진행 중 DB 정보 조회에 실패했습니다." + "\n" + error.getMessage());
            error.printStackTrace();
        }
    }
}
