package controller;

import domain.JanggiBoard;
import domain.position.Position;
import java.util.LinkedHashMap;
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
        Position movePosition = inputMovePosition();
        Position targetPosition = inputTargetPosition();
        JanggiBoard janggiBoard = new JanggiBoard(new LinkedHashMap<>());
        outputView.printBoard(janggiBoard);
    }

    private Position inputMovePosition() {
        String inputMovePosition = inputView.inputMovePiece();
        String[] parts = inputMovePosition.split(",");

        int row = Integer.parseInt(parts[0].trim());
        int column = Integer.parseInt(parts[1].trim());

        return new Position(row, column);
    }

    private Position inputTargetPosition() {
        String inputMovePosition = inputView.inputMovePiece();
        String[] parts = inputMovePosition.split(",");

        int row = Integer.parseInt(parts[0].trim());
        int column = Integer.parseInt(parts[1].trim());

        return new Position(row, column);
    }
}
