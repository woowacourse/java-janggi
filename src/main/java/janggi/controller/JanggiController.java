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
        BoardType boardType = readInitialBoardType();
        Janggi janggi = initializeBoard(boardType);

        while (!janggi.isGameOver()) {
            janggi.withBoard(outputView::printBoard);
            janggi = readAndPlay(janggi);
        }
    }

    private Janggi readAndPlay(Janggi janggi) {
        while (true) {
            try {
                Position from = readFromPosition();
                Position to = readToPosition();
                return janggi.play(from, to);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private BoardType readInitialBoardType() {
        while (true) {
            try {
                outputView.printBoardInitialTypeMessage();
                int boardTypeNumber = inputView.readBoardInitializeType();
                return BoardType.of(boardTypeNumber);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Janggi initializeBoard(BoardType boardType) {
        return Janggi.of(boardType.init());
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
