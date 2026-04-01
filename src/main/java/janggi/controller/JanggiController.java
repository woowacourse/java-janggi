package janggi.controller;

import janggi.model.Janggi;
import janggi.model.board.position.Column;
import janggi.model.board.position.Position;
import janggi.model.board.position.Row;
import janggi.model.initializer.BoardType;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.dto.GameStatus;
import java.util.List;

public class JanggiController {

    private final OutputView outputView;
    private final InputView inputView;

    public JanggiController(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        Janggi janggi = setUpJanggi();

        while (!janggi.isGameOver()) {
            outputView.printGameStatus(GameStatus.from(janggi));

            janggi = janggi.play(
                    readFromPosition(),
                    readToPosition()
            );
        }
    }

    private Janggi setUpJanggi() {
        outputView.printBoardInitialTypeMessage();
        BoardType boarType = inputView.readBoardInitializeType();
        return Janggi.of(boarType.getBoard());
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
        int rowNumber = positionInfo.get(rowIndex);

        if (rowNumber == 0) {
            rowNumber = 10;
        }

        int columnIndex = 1;
        Integer columnNumber = positionInfo.get(columnIndex);

        Row row = Row.of(rowNumber);
        Column column = Column.of(columnNumber);

        return new Position(row, column);
    }

}
