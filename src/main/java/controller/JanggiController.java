package controller;

import domain.Country;
import domain.TableSetting;
import java.util.ArrayList;
import java.util.List;
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
    }

    private List<TableSetting> makeTableSetting() {
        List<TableSetting> tableSettings = new ArrayList<>();
        for (Country country : Country.values()) {
            String input = inputView.readTableSetting(country.getName());
            List<String> tableNames = InputParser.parseTableSetting(input);

            TableSetting tableSetting = TableSetting.from(tableNames);
            tableSettings.add(tableSetting);
        }
        return tableSettings;
    }
}
