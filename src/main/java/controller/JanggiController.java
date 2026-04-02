package controller;

import domain.Position;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.BoardSnapshots;
import domain.board.TableSetting;
import domain.country.CountryType;
import java.util.List;
import view.CountryFormatter;
import view.InputParser;
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
        Board board = makeBoard();
        List<CountryType> playOrders = List.of(CountryType.CHO, CountryType.HAN);
        BoardSnapshots boardSnapshots = new BoardSnapshots();

        playTurn(board, playOrders, boardSnapshots);
    }

    private Board makeBoard() {
        TableSetting choTableSetting = readTableSetting(CountryType.CHO);
        TableSetting hanTableSetting = readTableSetting(CountryType.HAN);
        BoardFactory boardFactory = new BoardFactory();
        return boardFactory.create(choTableSetting, hanTableSetting);
    }

    private TableSetting readTableSetting(CountryType countryType) {
        while (true) {
            try {
                String input = inputView.readTableSetting(CountryFormatter.from(countryType));
                String tableNames = InputParser.parseTableSetting(input);

                return TableSetting.from(tableNames);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private void playTurn(Board board, List<CountryType> playOrders, BoardSnapshots boardSnapshots) {
        int turnIndex = 0;
        boolean isEnd = false;
        while (!isEnd) {
            CountryType countryType = playOrders.get(turnIndex);
            isEnd = checkEndAndMovePiece(board, countryType, boardSnapshots);

            turnIndex = (turnIndex + 1) % 2;
        }
    }

    private boolean checkEndAndMovePiece(Board board, CountryType countryType, BoardSnapshots boardSnapshots) {
        outputView.printBoard(board.getBoardSnapshot(countryType), board.getScores());

        boolean isEndWithGeneralCaught = movePiece(board, countryType);
        if (isEndWithGeneralCaught) {
            outputView.printEndWithCatchGeneral(countryType);
        }
        boolean isEndWithBoardRepeat = boardSnapshots.appearSamePositionThreeTurn(board.getBoardSnapshot(countryType));
        if (isEndWithBoardRepeat) {
            outputView.printEndWithBoardRepeat(board.getScores());
        }
        return isEndWithGeneralCaught || isEndWithBoardRepeat;
    }

    private boolean movePiece(Board board, CountryType countryType) {
        while (true) {
            try {
                Position from = makeFromPosition();
                board.validateFromPosition(from, countryType);
                Position to = makeToPosition();

                return board.checkEndAndPlay(from, to);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private Position makeFromPosition() {
        String input = inputView.readFromPosition();
        List<Integer> positions = InputParser.parsePosition(input);
        return new Position(positions.get(0), positions.get(1));
    }

    private Position makeToPosition() {
        String input = inputView.readToPosition();
        List<Integer> positions = InputParser.parsePosition(input);
        return new Position(positions.get(0), positions.get(1));
    }
}
