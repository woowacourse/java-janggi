package janggi.model;

import janggi.controller.JanggiController;
import janggi.model.initializer.OutsideTableSetting;
import janggi.view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {

        JanggiController controller = new JanggiController(
                new OutputView(),
                new OutsideTableSetting()
        );

        controller.initializeBoard();
    }
}
