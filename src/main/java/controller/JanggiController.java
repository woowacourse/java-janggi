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
import service.JanggiService;
import view.InputView;
import view.OutputView;

public final class JanggiController {

    private final JanggiService janggiService;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(JanggiService janggiService, InputView inputView, OutputView outputView) {
        this.janggiService = janggiService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    private void startGame(JanggiGame janggiGame) {
        outputView.printGameStart();
        outputView.printBoard(janggiGame.getBoard());
        while (!janggiGame.isFinished()) {
            Side currentTurn = janggiGame.currentTurn();

            // TODO command 받고 -> position 변환 이거 하나로 묶을 수 없는지 시도하기. depth 1로.
            String command = inputView.readCommand(currentTurn);
            if (command.equals("exit")) {
                outputView.printGameFinishedByCommand();
                janggiService.saveGame(janggiGame);
                return;
            }

            Intersection startPosition;
            try {
                startPosition = Intersection.parse(command);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                continue;
            }

            // TODO board말고 장기게임만 넘겨서 해결할 수 없을까?
            Board board = janggiGame.getBoard();
            outputView.printBoardWithMovable(board, board.getMovableIntersections(startPosition, currentTurn));

            readValidDestinationAndPrintBoard(janggiGame, board, startPosition, currentTurn);
        }

        outputView.printWinner(janggiGame.determineResult());
        janggiService.saveGame(janggiGame);
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
                janggiService.saveGame2(gameWrapper);
                return;
            }

            Intersection startPosition;
            try {
                startPosition = Intersection.parse(command);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                continue;
            }

            // TODO board말고 장기게임만 넘겨서 해결할 수 없을까?
            Board board = janggiGame.getBoard();
            outputView.printBoardWithMovable(board, board.getMovableIntersections(startPosition, currentTurn));

            readValidDestinationAndPrintBoard(janggiGame, board, startPosition, currentTurn);
        }

        outputView.printWinner(janggiGame.determineResult());
        janggiService.saveGame2(gameWrapper);
    }

    private void continueGame(long gameId) {
        GameWrapper gameWrapper = janggiService.loadGame(gameId);
        startGame(gameWrapper);
    }

    public void run() {
        outputView.printGameMenu();
        GameMenu gameMenu = readValidGameMenu();

        if (gameMenu == GameMenu.SHOW_PREVIOUS_GAMES) {
            List<GameSummary> gameSummaries = janggiService.loadAllGameSummaries();
            outputView.printGames(gameSummaries);

            if (!gameSummaries.isEmpty()) {
                int gameId = inputView.readGameNumber();
                continueGame(gameId);
            }
            // TODO 0이면 새 게임 시작. 다른 유효한 번호면 그 게임을 이어서 시작
            return;
        }

        // 새 게임 시작
        outputView.printGameStart();

        InitialPieces initialPieces = setUpInitialPieces();
        AlivePieces alivePieces = initialPieces.toAlivePieces();
        Board board = new Board(alivePieces);
        GameWrapper gameWrapper = janggiService.createGame(board);
        startGame(gameWrapper);
        /*
        JanggiGame janggiGame = new JanggiGame(board);

        outputView.printBoard(board);

        while (!janggiGame.isFinished()) {
            Side currentTurn = janggiGame.currentTurn();

            // TODO command 받고 -> position 변환 이거 하나로 묶을 수 없는지 시도하기. depth 1로.
            String command = inputView.readCommand(currentTurn);
            if (command.equals("exit")) {
                outputView.printGameFinishedByCommand();
                return;
            }

            Intersection startPosition;
            try {
                startPosition = Intersection.parse(command);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                continue;
            }

            outputView.printBoardWithMovable(board, board.getMovableIntersections(startPosition, currentTurn));

            readValidDestinationAndPrintBoard(janggiGame, board, startPosition, currentTurn);
        }

        outputView.printWinner(janggiGame.determineResult());*/
    }

    private GameMenu readValidGameMenu() {
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
            Board board,
            Intersection startPosition,
            Side currentTurn
    ) {
        while (true) {
            try {
                Intersection destination = inputView.readDestination();
                janggiGame.movePiece(startPosition, destination, currentTurn);
                outputView.printBoard(board);

                return destination;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
