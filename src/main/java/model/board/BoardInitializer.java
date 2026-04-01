package model.board;

import view.InputHandler;
import view.InputView;
import view.OutputView;

public class BoardInitializer {

    public void initialize(Board board, ArrangementType choType, ArrangementType hanType) {
        initializeArmy(board, Country.CHO, choType);
        initializeArmy(board, Country.HAN, hanType);
    }

    private void initializeArmy(Board board, Country country, ArrangementType type) {
        Army army = new Army(type.strategy());
        army.deployTo(board, country);
    }
}
