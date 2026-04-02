package controller;

import domain.Position;
import domain.board.Board;
import domain.board.BoardFactory;
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

        playTurn(board, playOrders);
    }

    private Board makeBoard() {
        TableSetting choTableSetting = readTableSetting(CountryType.CHO);
        TableSetting hanTableSetting = readTableSetting(CountryType.HAN);
        BoardFactory boardFactory = new BoardFactory();
        return boardFactory.create(choTableSetting, hanTableSetting);
    }

    private void playTurn(Board board, List<CountryType> playOrders) {
        int turnIndex = 0;
        boolean isEnd = false;
        while (!isEnd) {
            CountryType countryType = playOrders.get(turnIndex);
            outputView.printTurn(CountryFormatter.from(countryType));
            outputView.printScore(board.getScores());
            outputView.printBoard(board.getPieceInfos());

            isEnd = movePiece(board, countryType);
            turnIndex = (turnIndex + 1) % 2;
        }
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
