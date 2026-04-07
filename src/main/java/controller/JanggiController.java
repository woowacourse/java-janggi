package controller;

import domain.board.Board;
import domain.board.InitialPieces;
import domain.board.Intersection;
import domain.board.wing.Wings;
import domain.game.JanggiGame;
import domain.game.Side;
import domain.piece.AlivePieces;
import dto.GameMenu;
import dto.GameSummary;
import dto.GameWrapper;
import java.util.List;
import java.util.Map;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public final class JanggiController {

    private final JanggiService janggiService;
    private final InputView inputView;
    private final OutputView outputView;

    private final Map<GameMenu, Runnable> menuFlowHandler = Map.of(
            GameMenu.NEW_GAME, this::newGameFlow,
            GameMenu.SHOW_PREVIOUS_GAMES, this::loadGameFlow
    );

    public JanggiController(JanggiService janggiService, InputView inputView, OutputView outputView) {
        this.janggiService = janggiService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printWelcomeMessage();

        GameMenu selectedMenu;
        while ((selectedMenu = receiveValidGameMenu()).isDispatchable()) {
            dispatchMenu(selectedMenu);
        }

        outputView.printExitMessage();
    }

    private GameMenu receiveValidGameMenu() {
        outputView.printGameMenu();

        while (true) {
            try {
                int menuCommand = inputView.readMenuCommand();
                return GameMenu.from(menuCommand);
            } catch (NumberFormatException e) {
                outputView.printError("숫자만 입력할 수 있습니다.");
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void dispatchMenu(GameMenu selectedMenu) {
        final Runnable defaultFlow = () -> {
            throw new IllegalArgumentException(selectedMenu + "를 처리할 수 없습니다.");
        };

        menuFlowHandler.getOrDefault(selectedMenu, defaultFlow)
                .run();
    }

    private void loadGameFlow() {
        List<GameSummary> gameSummaries = janggiService.loadAllGameSummaries();
        outputView.printGames(gameSummaries);

        if (!gameSummaries.isEmpty()) {
            // TODO 이거 if == 0으로 분기 처리하는게 좀 별로다.
            int gameId = inputView.readGameNumber();
            if (gameId == 0) {
                newGameFlow();
            }
            continueGame(gameId);
        }
    }

    private void newGameFlow() {
        outputView.printGameStart();

        InitialPieces initialPieces = setUpInitialPieces();
        AlivePieces alivePieces = initialPieces.toAlivePieces();
        Board board = new Board(alivePieces);
        GameWrapper gameWrapper = janggiService.createGame(board);

        startGame(gameWrapper);
    }

    private void startGame(GameWrapper gameWrapper) {
        outputView.printGameStart();
        JanggiGame janggiGame = gameWrapper.game();
        outputView.printBoard(janggiGame.getBoard());
        while (!janggiGame.isFinished()) {
            Side currentTurn = janggiGame.currentTurn();

            // TODO command 받고 -> position 변환 이거 하나로 묶을 수 없는지 시도하기. depth 1로.
            String command = inputView.readCommand(currentTurn);
            if (command.equals("exit")) {
                outputView.printGameFinishedByCommand();
                janggiService.saveGame(gameWrapper);
                return;
            }

            Intersection startPosition;
            try {
                startPosition = Intersection.parse(command);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                continue;
            }

            outputView.printBoardWithMovable(
                    janggiGame.getBoard(),
                    janggiGame.getMovableIntersections(startPosition, currentTurn)
            );

            readValidDestinationAndPrintBoard(janggiGame, startPosition, currentTurn);
        }

        outputView.printWinner(janggiGame.determineResult());
        janggiService.saveGame(gameWrapper);
    }

    private void continueGame(long gameId) {
        GameWrapper gameWrapper = janggiService.loadGame(gameId);
        startGame(gameWrapper);
    }

    private InitialPieces setUpInitialPieces() {
        Wings choWings = readValidWings(Side.CHO);
        Wings hanWings = readValidWings(Side.HAN);

        return new InitialPieces(hanWings, choWings);
    }

    private Wings readValidWings(Side side) {
        while (true) {
            try {
                return inputView.readWings(side);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private Intersection readValidDestinationAndPrintBoard(
            JanggiGame janggiGame,
            Intersection startPosition,
            Side currentTurn
    ) {
        while (true) {
            try {
                Intersection destination = inputView.readDestination();
                janggiGame.movePiece(startPosition, destination, currentTurn);
                outputView.printBoard(janggiGame.getBoard());

                return destination;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
