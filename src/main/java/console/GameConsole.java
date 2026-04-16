package console;

import domain.board.Formation;
import domain.game.Game;
import domain.piece.Side;
import domain.vo.LoadGameDecision;
import domain.vo.Position;
import service.GameService;
import util.Parser;
import view.InputView;
import view.OutputView;

public class GameConsole {

    private final GameService gameService;

    private final InputView inputView;
    private final OutputView outputView;

    public GameConsole(GameService gameService, InputView inputView, OutputView outputView) {
        this.gameService = gameService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        if (gameService.existsGame() && readLoadGame()) {
            Game game = loadGame();
            processMove(game);
            return;
        }
        Game game = initGame();
        processMove(game);
    }

    private Game loadGame() {
        Game game = gameService.loadGame();
        outputView.printBoardStatus(game.getBoard());
        return game;
    }

    private Game initGame() {
        Formation choformation = readChoFormation();
        Formation hanformation = readHanFormation();
        Game game = gameService.finishGamesAndCreateGame(choformation, hanformation);
        outputView.printBoardStatus(game.getBoard());
        return game;
    }

    private void processMove(Game game) {
        while (!game.isGameEnd()) {
            Side currentTurn = game.getCurrentTurn();
            outputView.printCurrentTotalScore(game.calculateTotalScore(Side.CHO), game.calculateTotalScore(Side.HAN));
            outputView.printCurrentTurn(currentTurn);
            Position sourcePosition = readSourcePosition();
            Position targetPosition = readTargetPosition();
            try {
                gameService.moveAndSave(game, sourcePosition, targetPosition);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
            outputView.printBoardStatus(game.getBoard());
        }
        outputView.printResult(game.getCurrentTurn());
    }

    private Position readSourcePosition() {
        while (true) {
            try {
                int x = Parser.parseInput(inputView.readSourceXPosition());
                int y = Parser.parseInput(inputView.readSourceYPosition());
                return Position.of(x, y);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position readTargetPosition() {
        while (true) {
            try {
                int x = Parser.parseInput(inputView.readTargetXPosition());
                int y = Parser.parseInput(inputView.readTargetYPosition());
                return Position.of(x, y);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Formation readChoFormation() {
        while (true) {
            try {
                String choFormation = inputView.readChoFormation();
                return Formation.from(choFormation);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Formation readHanFormation() {
        while (true) {
            try {
                String hanFormation = inputView.readHanFormation();
                return Formation.from(hanFormation);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private boolean readLoadGame() {
        while (true) {
            try {
                String input = inputView.readLoadGame();
                return LoadGameDecision.from(input).shouldLoad();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
