package janggi;

import janggi.application.GameService;
import janggi.controller.JanggiController;
import janggi.infra.config.DataSourceConfig;
import janggi.infra.dao.JdbcGameDAO;
import janggi.infra.dao.JdbcPiecePositionDAO;
import janggi.infra.util.ConnectionProvider;
import janggi.infra.transaction.TransactionTemplate;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        new JanggiController(
                new InputView(),
                new OutputView(),
                new GameService(
                        new JdbcGameDAO(new ConnectionProvider(dataSourceConfig.dataSource())),
                        new JdbcPiecePositionDAO(new ConnectionProvider(dataSourceConfig.dataSource())),
                        new TransactionTemplate(dataSourceConfig.dataSource())
                )
        ).run();
    }

}
