package controller;

import java.util.List;

import model.board.*;
import model.game.JanggiGame;
import model.move.Move;
import model.position.Position;
import view.InputHandler;
import view.InputView;
import view.OutputView;

public class GameController {
    private final BoardInitializer boardInitializer;

    public GameController(BoardInitializer boardInitializer) {
        this.boardInitializer = boardInitializer;
    }

    public void start() {
        Board board = new Board();
        init(board);
        JanggiGame game = new JanggiGame(board);
        OutputView.printBoard(board);
        while (true) {
            runGame(game, board);
            if (gameOver(game, board)) break;
        }
    }

    private boolean gameOver(JanggiGame game, Board board) {
        if (game.isFinished()) {
            printResult(board, game);
            return true;
        }
        return false;
    }

    private void init(Board board) {
        ArrangementType choType = readArrangementType(Country.CHO);
        OutputView.printLine();
        ArrangementType hanType = readArrangementType(Country.HAN);

        boardInitializer.initialize(board, choType, hanType);
    }

    private ArrangementType readArrangementType(Country country) {
        OutputView.printArrangeCountry(country);
        return InputHandler.retry(() ->
                ArrangementType.from(InputView.readArrangement(country)));
    }

    private void runGame(JanggiGame game, Board board) {
        OutputView.printPositionCountry(game.turn());

        InputHandler.retry(() -> {
            List<Integer> startList = InputView.readStartPosition();
            List<Integer> endList = InputView.readEndPosition();
            Position from = Position.of(startList.get(0), startList.get(1));
            Position to = Position.of(endList.get(0), endList.get(1));
            Move move = new Move(from, to);
            game.move(move);
            OutputView.printBoard(board);
            return null;
        });
    }

    private void printResult(Board board, JanggiGame game) {
        OutputView.printWinner(game.winner());
        OutputView.printScore(Country.CHO, board.calculateScore(Country.CHO));
        OutputView.printScore(Country.HAN, board.calculateScore(Country.HAN));
    }
}
