package janggi.repository;

import janggi.config.AppConfig;
import janggi.repository.dao.GameEntityDao;
import janggi.repository.dao.PieceEntityDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class DatabaseTest {
    protected GameEntityDao gameEntityDao = new GameEntityDao();
    protected PieceEntityDao pieceEntityDao = new PieceEntityDao();
    protected Connection con;

    @BeforeEach
    void beforeEach() throws SQLException {
        con = DriverManager.getConnection(new AppConfig("application-test.properties").getDbUrl());

        con.setAutoCommit(false);
    }

    @AfterEach
    void afterEach() throws SQLException {
        con.rollback();
        con.close();
    }
}
