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
import dto.LoadCommand;
import dto.LoadCommand.Type;
import dto.MoveCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
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

    private final Map<LoadCommand.Type, Consumer<Long>> loadCommandHandler = Map.of(
            Type.NEW_GAME, newGameCommandNumber -> newGameFlow(),
            Type.LOAD_GAME, this::continueGame
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

        List<Long> gameNumbers = gameSummaries.stream()
                .map(GameSummary::id)
                .toList();
        List<Long> selectableNumbers = new ArrayList<>(gameNumbers);
        selectableNumbers.add(0L);

        while (true) {
            try {
                LoadCommand loadCommand = LoadCommand.from(inputView.readGameNumber());
                dispatchLoadCommand(loadCommand, selectableNumbers);
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void dispatchLoadCommand(LoadCommand loadCommand, List<Long> selectableNumbers) {
        final Consumer<Long> defaultConsumer = gameNumber -> {
            throw new IllegalArgumentException("핸들러에 등록되지 않은 커맨드입니다.");
        };

        long gameNumberToLoad = loadCommand.gameNumberToLoad();
        if (!selectableNumbers.contains(gameNumberToLoad)) {
            throw new IllegalArgumentException(gameNumberToLoad + "는 유효하지 않는 번호입니다.");
        }

        loadCommandHandler.getOrDefault(loadCommand.type(), defaultConsumer)
                .accept(gameNumberToLoad);
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
        JanggiGame janggiGame = setUpGame(gameWrapper);
        while (!janggiGame.isFinished()) {
            MoveCommand moveCommand;
            try {
                moveCommand = inputView.readMoveCommand(janggiGame.currentTurn());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                continue;
            }

            if (moveCommand.isExit()) {
                exitGame(gameWrapper);
                return;
            }

            processTurn(moveCommand, janggiGame);
        }

        finishGame(gameWrapper, janggiGame);
    }

    private JanggiGame setUpGame(GameWrapper gameWrapper) {
        outputView.printGameStart();
        JanggiGame janggiGame = gameWrapper.game();
        outputView.printBoard(janggiGame.getBoard());
        return janggiGame;
    }

    private void exitGame(GameWrapper gameWrapper) {
        outputView.printGameFinishedByCommand();
        janggiService.saveGame(gameWrapper);
    }

    private void processTurn(MoveCommand moveCommand, JanggiGame janggiGame) {
        Intersection startPosition = moveCommand.selectedToMove()
                .orElseThrow(() -> new IllegalArgumentException("이동할 좌표(x,y) 또는 종료(exit)를 입력해주세요."));
        Side currentTurn = janggiGame.currentTurn();

        outputView.printBoardWithMovable(
                janggiGame.getBoard(),
                janggiGame.getMovableIntersections(startPosition, currentTurn)
        );
        readValidDestinationAndPrintBoard(janggiGame, startPosition, currentTurn);
    }

    private void finishGame(GameWrapper gameWrapper, JanggiGame janggiGame) {
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
