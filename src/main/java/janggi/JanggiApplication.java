package janggi;

import janggi.controller.JanggiController;
import janggi.infra.connector.DatabaseConnector;
import janggi.infra.connector.MySQLConnector;
import janggi.infra.repository.piece_repository.JdbcPieceRepository;
import janggi.infra.repository.turn_repository.JdbcTurnRepository;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.ViewUtil;

public class JanggiApplication {

    public static void main(String[] args) {
        final DatabaseConnector connector = new MySQLConnector();

        final JanggiController janggiController = new JanggiController(
                new InputView(new ViewUtil()),
                new OutputView(new ViewUtil()),
                new JdbcPieceRepository(connector),
                new JdbcTurnRepository(connector)
        );
        janggiController.run();
    }
}
