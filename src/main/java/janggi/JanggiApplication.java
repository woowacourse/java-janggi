package janggi;

import janggi.repository.CsvInitialBoardProvider;
import janggi.repository.GameRepository;
import janggi.repository.InitialBoardProvider;
import janggi.service.JanggiGameService;
import janggi.dto.GameSummary;
import janggi.domain.JanggiGame;
import janggi.domain.Point;
import janggi.dto.GameStatusInfo;
import janggi.util.DatabaseInitializer;
import janggi.util.JdbcConnectionManager;
import janggi.repository.JdbcGameRepository;
import janggi.ui.InputView;
import janggi.ui.OutputView;
import java.util.List;

public class JanggiApplication {

    public static void main(String[] args) {
        JdbcConnectionManager connectionManager = new JdbcConnectionManager(
                "jdbc:h2:file:./storage/janggi;AUTO_SERVER=TRUE",
                "sa",
                ""
        );
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(connectionManager);
        databaseInitializer.init();

        GameRepository gameRepository = new JdbcGameRepository(connectionManager);
        InitialBoardProvider initialBoardProvider = new CsvInitialBoardProvider();
        JanggiGameService janggiGameService =
                new JanggiGameService(gameRepository, initialBoardProvider);

        Long gameId = selectGame(janggiGameService);
        playGame(janggiGameService, gameId);
    }

    private static Long selectGame(JanggiGameService janggiGameService) {
        String command = InputView.readGameCommand();
        if (InputView.isNewGameCommand(command)) {
            return janggiGameService.createGame();
        }
        if (InputView.isLoadGameCommand(command)) {
            return loadGame(janggiGameService);
        }
        throw new IllegalArgumentException("1 또는 2를 입력해 주세요.");
    }

    private static Long loadGame(JanggiGameService janggiGameService) {
        List<GameSummary> gameSummaries = janggiGameService.findAllGames();
        OutputView.printSavedGames(gameSummaries);
        return InputView.readGameId();
    }

    private static void playGame(JanggiGameService janggiGameService, Long gameId) {
        JanggiGame janggiGame = janggiGameService.loadGame(gameId);
        OutputView.printGameStatus(GameStatusInfo.from(janggiGame.getBoardStatus()));

        while (!janggiGame.isFinished()) {
            OutputView.printCurrentTurn(janggiGame.currentTurn().getName());
            List<Point> points = InputView.readPoints();
            janggiGameService.play(gameId, points.get(0), points.get(1));
            janggiGame = janggiGameService.loadGame(gameId);
            OutputView.printGameStatus(GameStatusInfo.from(janggiGame.getBoardStatus()));
        }
        OutputView.printWinner(janggiGame.getWinner());
    }
}
