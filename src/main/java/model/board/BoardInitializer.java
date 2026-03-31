package model.board;

import view.InputHandler;
import view.InputView;
import view.OutputView;

public class BoardInitializer {

    public void initialize(Board board) {
        initializeArmy(board, Country.CHO);
        OutputView.printLine();
        initializeArmy(board, Country.HAN);
    }

    private void initializeArmy(Board board, Country country) {
        OutputView.printArrangeCountry(country);
        Army army = InputHandler.retry(() -> {
            String input = InputView.readArrangement(country);
            ArrangementType type = ArrangementType.from(input);

            return new Army(type.strategy());
        });

        army.deployTo(board, country);
    }

}
