package controller;

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

        play(board);
    }

    private void play(Board board) {
        while (true) {
            outputView.printBoard(board);
            executeMove(board);
        }
    }

    private void executeMove(Board board) {
        while (true) {
            try {
                Position from = inputView.readSourcePosition();

                if (board.getPiece(from).isEmpty()) {
                    outputView.printError(new IllegalArgumentException("해당 위치에 움직일 기물이 없습니다. 다시 선택해주세요."));
                    continue;
                }

                Position to = inputView.readTargetPosition();

                board.move(from, to);
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
