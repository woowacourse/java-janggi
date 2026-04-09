package application;

import dao.GameDAO;
import domain.game.Game;
import domain.piece.Camp;
import view.*;

import java.util.NoSuchElementException;

public class Application {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {

        GameDAO gameDAO = new GameDAO();
        gameDAO.initTables();

        String menu = readValidMainMenu();

        if ("1".equals(menu)) {
            startNewGame(gameDAO);
            return;
        }
        loadPreviousGame(gameDAO);
    }

    private void startJanggi(Game game, int gameId, GameDAO gameDAO) {
        outputView.printBoard(game.board());
        playJanggi(game, gameId, gameDAO);
    }

    private void startNewGame(GameDAO gameDAO) {
        outputView.printSetUpOptions();
        int hanSetup = readSetUp(Camp.HAN);
        int choSetup = readSetUp(Camp.CHO);

        Game game = new Game(choSetup, hanSetup);
        int gameId = gameDAO.save(game);

        outputView.printNewGameStart(gameId);

        startJanggi(game, gameId, gameDAO);
    }

    private void loadPreviousGame(GameDAO gameDAO) {
        int gameId = readValidGameId();

        Game game = gameDAO.findBy(gameId);

        outputView.printLoadPreviousGame(gameId);

        startJanggi(game, gameId, gameDAO);
    }

    private void playJanggi(Game game, int gameId, GameDAO gameDAO) {
        progressJanggi(game, gameId, gameDAO);

        if (game.isNotEnoughPieces()) {
            outputView.printScore(game.choScore(), game.hanScore());
        }
        outputView.printWinner(game.winner());
    }

    private void progressJanggi(Game game, int gameId, GameDAO gameDAO) {
        while (!game.isFinished()) {
            outputView.printTurnPrompt(game.currentTurn());

            try {
                GameCommand command = inputView.readCommand();
                execute(game, command, gameId, gameDAO);
                outputView.printBoard(game.board());
            } catch (IllegalArgumentException | NoSuchElementException exception) {
                outputView.printError(exception.getMessage());
            } catch (IllegalStateException exception) {
                return;
            }
        }
    }

    private int readSetUp(Camp camp) {
        while (true) {
            outputView.printSetUpPrompt(camp);

            try {
                return inputView.readSetUp();
            } catch (IllegalArgumentException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    private void execute(Game game, GameCommand command, int gameId, GameDAO gameDAO) {
        command.execute(game);

        if (command instanceof MoveCommand moveCommand) {
            gameDAO.updateMove(gameId, game, moveCommand.from(), moveCommand.to());
            return;
        }

        if (command instanceof PassCommand) {
            gameDAO.updateGameStatus(gameId, game);
        }
    }

    private String readValidMainMenu() {
        while (true) {
            outputView.printMainMenu();
            try {
                return inputView.readMainMenu();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private int readValidGameId() {
        while (true) {
            outputView.printLoadGameMenu();
            try {
                return inputView.readGameId();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
