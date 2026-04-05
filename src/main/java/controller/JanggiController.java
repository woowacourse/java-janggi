package controller;

import domain.Board;
import domain.Country;
import domain.Position;
import domain.TableSetting;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
        Map<Country, TableSetting> tableSettings = makeTableSetting();
        Board board = Board.create(tableSettings.get(Country.CHO), tableSettings.get(Country.HAN));
        List<Country> playOrders = List.of(Country.CHO, Country.HAN);

        playTurn(board, playOrders);
    }

    private Map<Country, TableSetting> makeTableSetting() {
        Map<Country, TableSetting> tableSettings = new LinkedHashMap<>();
        for (Country country : Country.values()) {
            tableSettings.put(country, readTableSetting(country));
        }
        return tableSettings;
    }

    private void playTurn(Board board, List<Country> playOrders) {
        int turnIndex = 0;
        while (true) {
            Country country = playOrders.get(turnIndex);
            Country otherSide = playOrders.get((turnIndex + 1) % 2);
            outputView.printTurn(CountryFormatter.from(country));
            outputView.printBoard(board.getPieceInfos());

            if (movePiece(board, country)) {
                outputView.printWinner(CountryFormatter.from(country), CountryFormatter.from(otherSide));
                return;
            }
            turnIndex = (turnIndex + 1) % 2;
        }
    }

    private TableSetting readTableSetting(Country country) {
        while (true) {
            try {
                String input = inputView.readTableSetting(CountryFormatter.from(country));
                String tableNames = InputParser.parseTableSetting(input);

                return TableSetting.from(tableNames);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private boolean movePiece(Board board, Country country) {
        while (true) {
            try {
                Position from = makeFromPosition();
                board.validateFromPosition(from, country);
                Position to = makeToPosition();
                from.validatePositions(to);

                return board.move(from, to);
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
