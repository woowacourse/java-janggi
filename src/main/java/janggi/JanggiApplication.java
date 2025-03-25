package janggi;

import janggi.controller.JanggiController;
import janggi.infra.connector.MySQLConnector;
import janggi.infra.repository.piece_repository.JdbcPieceRepository;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        final JanggiController janggiController = new JanggiController(
                new InputView(),
                new OutputView(),
                new JdbcPieceRepository(new MySQLConnector())
        );
        janggiController.run();
    }
}
