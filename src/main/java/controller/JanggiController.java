package controller;

import domain.Board;
import domain.Country;
import domain.Position;
import domain.TableSetting;
import java.util.ArrayList;
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
        List<TableSetting> tableSettings = makeTableSetting();
        Board board = Board.create(tableSettings.get(1), tableSettings.get(0));
        List<Country> playOrders = List.of(Country.CHO, Country.HAN);

        playTurn(board, playOrders);
    }

    private List<TableSetting> makeTableSetting() {
        List<TableSetting> tableSettings = new ArrayList<>();
        for (Country country : Country.values()) {
            TableSetting tableSetting = readTableSetting(country);
            tableSettings.add(tableSetting);
        }
        return tableSettings;
    }

    private void playTurn(Board board, List<Country> playOrders) {
        int turnIndex = 0;
        while (true) {
            Country country = playOrders.get(turnIndex);
            outputView.printTurn(CountryFormatter.from(country));
            outputView.printBoard(board.getPieceInfos());

            movePiece(board, country);
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

    private void movePiece(Board board, Country country) {
        while (true) {
            try {
                Position from = makeFromPosition();
                board.validateFromPosition(from, country);
                Position to = makeToPosition();

                board.move(from, to);
                return;
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
