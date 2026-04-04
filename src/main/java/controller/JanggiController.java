package controller;

import domain.Board;
import domain.BoardFactory;
import domain.Formation;
import domain.Game;
import domain.Position;
import domain.Side;
import util.Parser;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Game game = initGame();
        processMove(game);
    }

    private Game initGame() {
        Formation choformation = readChoFormation();
        Formation hanformation = readHanFormation();

        Board board = BoardFactory.createBoard(choformation, hanformation);
        Game game = new Game(board);
        outputView.printBoardStatus(game.getBoard());
        return game;
    }

    private void processMove(Game game) {
        while (!game.isGameEnd()) {
            Side currentTurn = game.getCurrentTurn();
            outputView.printCurrentTurn(currentTurn);
            Position sourcePosition = readSourcePosition();
            Position targetPosition = readTargetPosition();
            try {
                game.move(sourcePosition, targetPosition);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
            outputView.printBoardStatus(game.getBoard());
        }
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
}
