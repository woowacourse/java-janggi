package controller;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.InitializeSetting;
import domain.board.Position;
import view.InputView;
import view.OutputView;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        try {
            InitializeSetting choSetting = inputView.readInitialSetting("초(CHO)");
            InitializeSetting hanSetting = inputView.readInitialSetting("한(HAN)");

            Board board = BoardFactory.createBoard(choSetting, hanSetting);

            play(board);
        } catch (Exception e) {
            outputView.printError(e);
        }
    }

    private void play(Board board) {
        while (true) {
            try {
                outputView.printBoard(board);
                Position[] positions = inputView.readMoveCommand();

                Position from = positions[0];
                Position to = positions[1];

                board.move(from, to);
            } catch (Exception e) {
                outputView.printError(e);
            }
        }
    }
}
