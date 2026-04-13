package janggi;

import janggi.controller.JanggiController;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:./janggi;MODE=MySQL;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM 'classpath:schema.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        JanggiController janggiController = JanggiController.startJanggi(dataSource);
        janggiController.run();
    }
}
