package janggi;

import janggi.db.DBConnection;
import janggi.db.DBInitializer;
import janggi.repository.JdbcGameDao;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.sql.Connection;
import java.sql.SQLException;

public class TestMain {

    public static void main(String[] args) throws SQLException {
        Connection conn = DBConnection.getConnection();
        DBInitializer.initialize(conn);
        new JanggiController2(new InputView(), new OutputView(), new JanggiService(new JdbcGameDao(conn))).run();
    }
}
