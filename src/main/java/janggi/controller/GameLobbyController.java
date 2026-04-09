package janggi.controller;

import janggi.domain.game.GameManager;
import janggi.domain.game.Side;
import janggi.dto.GameSessionDTO;
import janggi.service.JanggiService;
import janggi.util.SideDisplayNameMapper;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.UserCommand;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GameLobbyController {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public GameLobbyController(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public GameManager enterLobby(Connection connection) throws SQLException {
        if (gameNotExist(connection)) {
            return initialGame(connection);
        }
        if (resumeGame()) {
            return loadExistingGame(connection);
        }
        return generateNewGame(connection);
    }

    private boolean gameNotExist(Connection connection) throws SQLException {
        return janggiService.activeGames(connection).isEmpty();
    }

    private GameManager initialGame(Connection connection) throws SQLException {
        outputView.printGameNotExist();
        return generateNewGame(connection);
    }

    private boolean resumeGame() {
        outputView.printSelectGameData();
        UserCommand userCommand = UserCommand.from(inputView.readUserCommand());
        return userCommand.confirmed();
    }

    private GameManager loadExistingGame(Connection connection) throws SQLException {
        List<GameSessionDTO> activeGames = janggiService.activeGames(connection);
        activeGames.forEach(outputView::printActiveGameInfo);
        outputView.printSelectGameId();
        long selectedGameId = inputView.readGameId();
        return janggiService.loadGameSession(connection, selectedGameId);
    }

    private GameManager generateNewGame(Connection connection) throws SQLException {
        outputView.printGenerateGameData();
        String choName = readPlayerName(Side.CHO);
        String hanName = readPlayerName(Side.HAN);
        return janggiService.createNewSession(connection, choName, hanName);
    }

    private String readPlayerName(Side side) {
        outputView.printPlayerNameNotice(SideDisplayNameMapper.toDisplayName(side));
        return inputView.readPlayerName();
    }
}
