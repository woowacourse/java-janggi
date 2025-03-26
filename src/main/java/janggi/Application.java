package janggi;

import janggi.controller.JanggiController;
import janggi.domain.JanggiGame;
import janggi.domain.board.BoardSetup;
import janggi.domain.board.InitialBoard;
import janggi.domain.board.PlayingBoard;
import janggi.domain.gameState.BlueTurn;
import janggi.domain.piece.TeamColor;
import janggi.service.JanggiDBService;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiDBService janggiDBService = new JanggiDBService();

        JanggiGame janggiGame = getJanggiGame(inputView, janggiDBService);

        JanggiController controller = new JanggiController(inputView, outputView, janggiDBService, janggiGame);
        controller.run();
    }

    private static InitialBoard setupBoard(InputView inputView) {
        BoardSetup redSetup = getBoardSetup(inputView, TeamColor.RED);
        BoardSetup blueSetup = getBoardSetup(inputView, TeamColor.BLUE);
        return InitialBoard.createBoard(redSetup, blueSetup);
    }

    private static BoardSetup getBoardSetup(InputView inputView, TeamColor teamColor) {
        int setNumber = inputView.readBoardSetup(teamColor);
        return BoardSetup.from(setNumber);
    }

    private static JanggiGame getJanggiGame(InputView inputView, JanggiDBService janggiDBService) {
        Optional<Integer> gameId = janggiDBService.getInProgressGameId();
        if (gameId.isPresent()) {
            return janggiDBService.getInProgressGame(gameId.get());
        }

        InitialBoard initialBoard = getWithRetry(() -> setupBoard(inputView));
        PlayingBoard playingBoard = new PlayingBoard(initialBoard.getInitialBoard());
        JanggiGame janggiGame = new JanggiGame(new BlueTurn(playingBoard), new HashMap<>());

        janggiDBService.saveInitialBoard(initialBoard.getInitialBoard());
        janggiDBService.saveStartSate(janggiGame.getTurnColor());

        return janggiGame;
    }

    public static <T> T getWithRetry(Supplier<T> task) {
        while (true) {
            try {
                return task.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
