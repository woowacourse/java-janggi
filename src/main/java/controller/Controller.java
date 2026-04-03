package controller;

import domain.Game;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.InitializeSetting;
import domain.board.Position;
import view.InputView;
import view.OutputView;

import java.util.function.Supplier;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        InitializeSetting choSetting = retry(() -> inputView.readInitialSetting("초(CHO)"));
        InitializeSetting hanSetting = retry(() -> inputView.readInitialSetting("한(HAN)"));
        Board board = BoardFactory.createBoard(choSetting, hanSetting);
        Game game = new Game(board);

        play(game);
    }

    private void play(Game game) {
        while (true) {
            outputView.printBoard(game.getBoard());
            executeMove(game);
        }
    }

    private void executeMove(Game game) {
        while (true) {
            try {
                Position from = inputView.readSourcePosition();

                game.validateMoveAblePiece(from);

                Position to = inputView.readTargetPosition();

                game.move(from, to);

                break;
            } catch (IllegalArgumentException | IllegalStateException e) {
                outputView.printError(e);
            }
        }
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e);
            }
        }
    }
}
