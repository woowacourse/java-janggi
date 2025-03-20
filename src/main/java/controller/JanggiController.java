package controller;

import domain.JanggiBoard;
import domain.Position;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiBoard janggiBoard;

    public JanggiController(InputView inputView, OutputView outputView, JanggiBoard janggiBoard) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiBoard = janggiBoard;
    }


    public void run() {
        List<String> playerNames = inputView.readPlayerNames();
        outputView.displayPlayerInfo(playerNames);
        outputView.printJanggiBoard(janggiBoard.getBoard());
        while (true) {
            List<Integer> startRowAndColumn = inputView.readMovePiecePosition();
            List<Integer> targetRowAndColumn = inputView.readTargetPosition();
            Position startPosition = new Position(startRowAndColumn.getFirst(), startRowAndColumn.getLast());
            Position targetPosition = new Position(targetRowAndColumn.getFirst(), targetRowAndColumn.getLast());
            janggiBoard.move(startPosition, targetPosition);
            outputView.printJanggiBoard(janggiBoard.getBoard());
        }
    }
}
