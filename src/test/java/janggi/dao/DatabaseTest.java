package janggi.dao;

import janggi.dao.game.GameDao;
import janggi.dao.game.JdbcGameDao;
import janggi.dao.piece.JdbcPieceDao;
import janggi.infra.DbProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class DatabaseTest {
    protected GameDao gameDao = new JdbcGameDao();
    protected JdbcPieceDao pieceEntityDao = new JdbcPieceDao();
    protected Connection con;

    @BeforeEach
    void beforeEach() throws SQLException {
        con = DriverManager.getConnection(
                new DbProperties("application-test.properties").getDbUrl()
        );
        con.setAutoCommit(false);
    }

    @AfterEach
    void afterEach() throws SQLException {
        con.rollback();
        con.close();
    }
}
