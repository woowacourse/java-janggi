package controller;

import domain.JanggiGame;
import domain.Position;
import domain.boardgenerator.JanggiBoardGenerator;
import domain.game.GameService;
import domain.palace.Palace;
import domain.player.Player;
import domain.player.PlayerService;
import domain.player.Players;
import gameloader.GameChecker;
import gameloader.GameDataHandler;
import java.sql.SQLException;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final GameDataHandler gameDataHandler;
    private final GameChecker gameChecker;
    private final PlayerService playerService;
    private final GameService gameService;


    public JanggiController(InputView inputView, OutputView outputView, PlayerService playerService
            , GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.playerService = playerService;
        this.gameService = gameService;
        this.gameDataHandler = new GameDataHandler();
        this.gameChecker = new GameChecker();
    }

    public void run() throws SQLException {
        int gameId = inputView.readGameId();
        boolean isGameExist = gameChecker.checkIfGameExists(gameId);

        // 게임이 존재하면 불러오기
        if (isGameExist) {
            // 기존 게임 데이터를 불러오기
            loadExistingGame(gameId);
        }
        if (!isGameExist) {
            // 새 게임 시작
            startNewGame(gameId);
        }

    }

    private void loadExistingGame(int gameId) throws SQLException {

    }

    private void startNewGame(int gameId) {
        try {
            gameService.createGame(gameId);
            List<String> playerNames = inputView.readPlayerNames();
            Players players = playerService.savePlayer(playerNames, gameId);
            outputView.displayPlayerInfo(players);

            JanggiGame janggiGame = new JanggiGame(new JanggiBoardGenerator(), players, new Palace());
            outputView.displayJanggiBoard(janggiGame.getBoardState());
            while (true) {
                Player thisTurnPlayer = janggiGame.getThisTurnPlayer();
                gameProcess(thisTurnPlayer, janggiGame);
                outputView.displayJanggiBoard(janggiGame.getBoardState());
                if (janggiGame.checkKingIsDead()) {
                    outputView.displayJanggiBoard(janggiGame.getBoardState());
                    outputView.displayGameResult(thisTurnPlayer);
                    return;
                }
            }
        } catch (SQLException se) {
            outputView.displayErrorMessage(se.getMessage());
        }
    }

    private void gameProcess(Player thisTurnPlayer, JanggiGame janggiGame) {
        while (true) {
            try {
                List<Integer> startRowAndColumn = inputView.readMovePiecePosition(thisTurnPlayer);
                List<Integer> targetRowAndColumn = inputView.readTargetPosition(thisTurnPlayer);
                Position startPosition = new Position(startRowAndColumn.getFirst(), startRowAndColumn.getLast());
                Position targetPosition = new Position(targetRowAndColumn.getFirst(), targetRowAndColumn.getLast());
                janggiGame.move(startPosition, targetPosition);
                return;
            } catch (IllegalArgumentException iae) {
                outputView.displayErrorMessage(iae.getMessage());
            }
        }


    }
}
