package janggi.controller;

import janggi.model.BoardType;
import janggi.model.Janggi;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiController {

    private final OutputView outputView;
    private final InputView inputView;

    public JanggiController(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        int boardType = readInitialBoardType();
        Janggi janggi = initializeBoard(boardType);

        while (!janggi.isGameOver()) {
            janggi.withBoard(outputView::printBoard);
            Position from = readFromPosition();
            Position to = readToPosition();
            janggi = janggi.play(from, to);
        }
    }

    private int readInitialBoardType() {
        outputView.printBoardInitialTypeMessage();
        return inputView.readBoardInitializeType();
    }

    private Janggi initializeBoard(int boardType) {
        return Janggi.of(BoardType.of(boardType).init());
    }

    private Position readFromPosition() {
        outputView.printFromPositionMessage();
        return convertPositionInfoToPosition(inputView.readPosition());
    }

    private Position readToPosition() {
        outputView.printToPositionMessage();
        return convertPositionInfoToPosition(inputView.readPosition());
    }

    private Position convertPositionInfoToPosition(List<Integer> positionInfo) {
        int rowIndex = 0;
        int columnIndex = 1;

        Row row = Row.of(positionInfo.get(rowIndex));
        Column column = Column.of(positionInfo.get(columnIndex));

        return new Position(row, column);
    }
}
