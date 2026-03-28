package janggi.controller;

import janggi.model.Janggi;
import janggi.model.initializer.InsideTableSetting;
import janggi.model.initializer.LeftSidedTableSetting;
import janggi.model.initializer.OutsideTableSetting;
import janggi.model.initializer.RightSidedTableSetting;
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
        int leftSideTableOption = 1;
        int rightSideTableOption = 2;
        int insideTableOption = 3;
        int outsideTableOption = 4;

        if (boardType == leftSideTableOption) {
            return Janggi.of(new LeftSidedTableSetting().init());
        }
        if (boardType == rightSideTableOption) {
            return Janggi.of(new RightSidedTableSetting().init());
        }
        if (boardType == insideTableOption) {
            return Janggi.of(new InsideTableSetting().init());
        }
        if (boardType == outsideTableOption) {
            return Janggi.of(new OutsideTableSetting().init());
        }

        throw new IllegalArgumentException("유효한 유형 번호를 입력하세요.");
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
