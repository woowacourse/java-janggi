package janggi;

import janggi.controller.JanggiController;
import janggi.repository.util.ConnectionProvider;
import janggi.repository.util.JdbcConnectionProvider;
import janggi.repository.util.JdbcTransactionManager;
import janggi.repository.util.SchemaInitializer;
import janggi.repository.util.TransactionManager;
import janggi.service.DefaultJanggiService;
import janggi.service.JanggiService;
import janggi.view.ApplicationView;
import janggi.view.input.ConsoleReader;
import janggi.view.output.ConsoleWriter;

public class Application {

    public static void main(String[] args) {
        ConnectionProvider connectionProvider = new JdbcConnectionProvider();
        SchemaInitializer schemaInitializer = new SchemaInitializer(connectionProvider);
        schemaInitializer.init();

        ApplicationView view = new ApplicationView(new ConsoleWriter(), new ConsoleReader());

        TransactionManager transactionManager = new JdbcTransactionManager(connectionProvider);
        JanggiService janggiService = new DefaultJanggiService(transactionManager);
        JanggiController janggi = new JanggiController(view, janggiService);

        janggi.process();
    }
}
