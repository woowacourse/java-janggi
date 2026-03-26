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
        Board board = new Board(tableSettings.get(1), tableSettings.get(0));
        List<Country> playOrders = List.of(Country.CHO, Country.HAN);

        playTurn(board, playOrders);
    }

    private List<TableSetting> makeTableSetting() {
        List<TableSetting> tableSettings = new ArrayList<>();
        for (Country country : Country.values()) {
            String input = inputView.readTableSetting(CountryFormatter.from(country));
            String tableNames = InputParser.parseTableSetting(input);

            TableSetting tableSetting = TableSetting.from(tableNames);
            tableSettings.add(tableSetting);
        }
        return tableSettings;
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

    private void playTurn(Board board, List<Country> playOrders) {
        int turnIndex = 0;
        while (true) {
            Country country = playOrders.get(turnIndex);
            outputView.printTurn(CountryFormatter.from(country));
            outputView.printBoard(board.getPieceInfos());

            Position from = makeFromPosition();
            board.validateFromPosition(from, country);
            Position to = makeToPosition();

            board.move(from, to);
            turnIndex = turnIndex + 1 % 2;
        }
    }
}
